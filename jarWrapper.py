import argparse
from subprocess import *
import sys

jarfilename = sys.argv[1]
indexfilepath = sys.argv[2]
searchstring = sys.argv[3]

def jarWrapper(*args):
    process = Popen(['java', '-jar']+list(args), stdout=PIPE, stderr=PIPE)
    ret = []
    while process.poll() is None:
        line = process.stdout.readline()
        if line != '' and line.endswith('\n'):
            ret.append(line[:-1])
    stdout, stderr = process.communicate()
    ret += stdout.split('\n')
    if stderr != '':
        ret += stderr.split('\n')
    ret.remove('')
    return ret

def main():
    # args = ['Lucenesearch.jar', '/Users/jebaraj/IndexOutputJSON/', 'Ora Et Labora'] # Any number of args to be passed to the jar file
    args = [jarfilename, indexfilepath, searchstring] # Any number of args to be passed to the jar file
    result = jarWrapper(*args)
    print result
    return result


# run the program
main()
