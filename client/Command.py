class Command(object):
    def __init__(self, command, center_params, docker_params):
        self.center_params = center_params
        self.docker_params = docker_params
        self.command = command

    def __init__(self):
        object.__init__(self)
