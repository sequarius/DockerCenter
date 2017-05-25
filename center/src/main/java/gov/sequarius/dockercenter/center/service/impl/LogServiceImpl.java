package gov.sequarius.dockercenter.center.service.impl;

import gov.sequarius.dockercenter.center.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * Created by Sequarius on 2017/5/17.
 */
@Service
@Slf4j
public class LogServiceImpl implements LogService {
    private File LOG_SAVED_BASE_DIR;
    private File LOG_MERGER_BASE_DIR;
    private Long MAX_FILE_SIZE;
    private DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
    Map<String,Integer> lastLogFileName=new HashMap<>();
    @Override
    public Boolean mergerLog(Long startTime,String imageName) {
        File logDir=new File(LOG_SAVED_BASE_DIR,imageName);
        Date date=new Date(startTime);
        List<File> mergeList=new ArrayList<>();
        //to find the log should to merge;
        for (File file : logDir.listFiles()) {
            try {
                //to get the log date
                if(dateFormat.parse(file.getName().substring(0,20)).after(date)){
                    mergeList.add(file);
                }
            } catch (ParseException e) {
                log.error(e.getMessage(),e);
            }
        }
        return true;
    }

    private void mergeLog(List<File> logs,String imageNames){
        File lastLogFile=getLastLogFile(imageNames);
        if(lastLogFile.length()>MAX_FILE_SIZE){
            File file=new File(LOG_MERGER_BASE_DIR,imageNames+lastLogFileName.get(imageNames)+1);
        }
    }
    /**
     * This merges a bunch of temporary flat files
     *
     * @param files      The {@link List} of sorted {@link File}s to be merged.
     * @param distinct   Pass <code>true</code> if duplicate lines should be
     *                   discarded.
     * @param outputfile The output {@link File} to merge the results to.
     * @param cmp        The {@link Comparator} to use to compare
     *                   {@link String}s.
     * @param cs         The {@link Charset} to be used for the byte to
     *                   character conversion.
     * @param append     Pass <code>true</code> if result should append to
     *                   {@link File} instead of overwrite. Default to be false
     *                   for overloading methods.
     * @param usegzip    assumes we used gzip compression for temporary files
     * @return The number of lines sorted.
     * @throws IOException generic IO exception
     * @since v0.1.4
     */
    public int mergeSortedFiles(List<File> files, File outputfile,
                                       final Comparator<String> cmp, Charset cs, boolean distinct,
                                       boolean append, boolean usegzip) throws IOException {
        ArrayList<BinaryFileBuffer> bfbs = new ArrayList<>();
        for (File f : files) {
            final int BUFFERSIZE = 2048;
            InputStream in = new FileInputStream(f);
            BufferedReader br;
            if (usegzip) {
                br = new BufferedReader(
                        new InputStreamReader(
                                new GZIPInputStream(in,
                                        BUFFERSIZE), cs));
            } else {
                br = new BufferedReader(new InputStreamReader(
                        in, cs));
            }
            BinaryFileBuffer bfb = new BinaryFileBuffer(br);
            bfbs.add(bfb);
        }
        BufferedWriter fbw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outputfile, append), cs));
        int rowcounter = mergeSortedFiles(fbw, cmp, distinct, bfbs);
        for (File f : files) {
            f.delete();
        }
        return rowcounter;
    }

    /**
     * This merges several BinaryFileBuffer to an output writer.
     *
     * @param fbw      A buffer where we write the data.
     * @param cmp      A comparator object that tells us how to sort the
     *                 lines.
     * @param distinct Pass <code>true</code> if duplicate lines should be
     *                 discarded.
     * @param buffers  Where the data should be read.
     * @return The number of lines sorted.
     * @throws IOException generic IO exception
     */
    public static int mergeSortedFiles(BufferedWriter fbw,
                                       final Comparator<String> cmp, boolean distinct,
                                       List<BinaryFileBuffer> buffers) throws IOException {
        PriorityQueue<BinaryFileBuffer> pq = new PriorityQueue<>(
                11, new Comparator<BinaryFileBuffer>() {
            @Override
            public int compare(BinaryFileBuffer i,
                               BinaryFileBuffer j) {
                return cmp.compare(i.peek(), j.peek());
            }
        });
        for (BinaryFileBuffer bfb : buffers) {
            if (!bfb.empty()) {
                pq.add(bfb);
            }
        }
        int rowcounter = 0;
        try {
            if (!distinct) {
                while (pq.size() > 0) {
                    BinaryFileBuffer bfb = pq.poll();
                    String r = bfb.pop();
                    fbw.write(r);
                    fbw.newLine();
                    ++rowcounter;
                    if (bfb.empty()) {
                        bfb.fbr.close();
                    } else {
                        pq.add(bfb); // add it back
                    }
                }
            } else {
                String lastLine = null;
                if (pq.size() > 0) {
                    BinaryFileBuffer bfb = pq.poll();
                    lastLine = bfb.pop();
                    fbw.write(lastLine);
                    fbw.newLine();
                    ++rowcounter;
                    if (bfb.empty()) {
                        bfb.fbr.close();
                    } else {
                        pq.add(bfb); // add it back
                    }
                }
                while (pq.size() > 0) {
                    BinaryFileBuffer bfb = pq.poll();
                    String r = bfb.pop();
                    // Skip duplicate lines
                    if (cmp.compare(r, lastLine) != 0) {
                        fbw.write(r);
                        fbw.newLine();
                        lastLine = r;
                    }
                    ++rowcounter;
                    if (bfb.empty()) {
                        bfb.fbr.close();
                    } else {
                        pq.add(bfb); // add it back
                    }
                }
            }
        } finally {
            fbw.close();
            for (BinaryFileBuffer bfb : pq) {
                bfb.close();
            }
        }
        return rowcounter;
    }



    private File getLastLogFile(String imageNames) {
        Integer fileIndex = lastLogFileName.get(imageNames);
        File lastLogFile = null;
        while (fileIndex==null){
            Integer maxIndex=0;
            lastLogFile=new File(LOG_MERGER_BASE_DIR,imageNames+maxIndex);
            if(!lastLogFile.exists()){
                fileIndex=maxIndex;
                lastLogFileName.put(imageNames,fileIndex);
            }
        }
        return lastLogFile;
    }

    /**
     * This is essentially a thin wrapper on top of a BufferedReader... which keeps
     * the last line in memory.
     */
    final class BinaryFileBuffer {
        public BinaryFileBuffer(BufferedReader r) throws IOException {
            this.fbr = r;
            reload();
        }

        public void close() throws IOException {
            this.fbr.close();
        }

        public boolean empty() {
            return this.cache == null;
        }

        public String peek() {
            return this.cache;
        }

        public String pop() throws IOException {
            String answer = peek().toString();// make a copy
            reload();
            return answer;
        }

        private void reload() throws IOException {
            this.cache = this.fbr.readLine();
        }

        public BufferedReader fbr;

        private String cache;

    }
}


