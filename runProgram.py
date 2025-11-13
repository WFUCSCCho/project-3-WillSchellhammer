#Note: Doesn't work for me for some reason (it works in the command box though)

import os
import subprocess

folderPath = os.getcwd()
print(folderPath)
filePath = os.path.join("src", "Proj3.java")

for i in range (1,13):
    subprocess.run(["java",filePath,"steam_games.csv","quick", str(pow(2,i))], cwd=folderPath)

# for i in range (1,13):
#     subprocess.run("java " + filePath + " " + "steam_games.csv " + "transposition " + str(pow(2,i)), shell=True)
#
# for i in range (1,13):
#     subprocess.run("java\"" + filePath + "\"" + "steam_games.csv" + "merge" + str(pow(2,i)), shell=True)
#
# for i in range (1,13):
#     subprocess.run(["java","\"" + filePath + "\"","steam_games.csv","heap", str(pow(2,i))], shell=True)
#
# for i in range (1,13):
#     subprocess.run(["java","\"" + filePath + "\"","steam_games.csv","quick", str(pow(2,i))], shell=True)