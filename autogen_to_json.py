import os
import json
import sys

src_folder = "./BaseProject/src/main/java/frc/robot/autogen/"
web_src_folder = "./SwerveCreator/static/jinja/"
turn_src_folder = src_folder + "TurnMotor/"
drive_src_folder = src_folder + "DriveMotor/"
encoder_src_folder = src_folder + "Encoder/"
gyro_src_folder = src_folder + "Gyro/"

sys.exit("DEPRECATED, USE AUTOGENv2")

def rreplace(s, old, new, o):
    li = s.rsplit(old, o)
    return new.join(li)

def stripClosedBrakcets(s: str):
    count = 0
    for c in s:
        if c == '{':
            count += 1
        elif c == '}':
            count -= 1
    return rreplace(s, "}\n", "", abs(count))

##### TURN MOTORS #####

def scrapeTurnToJson(srcfile: str, filename: str):
    lines = []
    with open(srcfile, 'r') as file:
        for line in file:
            lines.append(line.rstrip())

    if lines[0] != "//JINJA":
        return
    
    imports = ""
    vars = ""
    initialize = ""
    setrotation = ""
    getrotation = ""
    resynchronizeTurnEncoder = ""

    state = 0

    for line in lines:
        if state == 0:
            if line == "//FILE START":
                state = 1
        elif state == 1:
            if line.strip().startswith("public class"):
                state = 2
            else:
                imports += line + "\n"
        elif state == 2:
            if line.strip().startswith("@Override"):
                state = 3
            else:
                vars += line + "\n"
        elif state == 3:
            subline = line.strip().split(" ")[2]
            if subline.strip().startswith("initialize"):
                state = 4
            elif subline.strip().startswith("setRotation"):
                state = 5
            elif subline.strip().startswith("getRotation"):
                state = 6
            elif subline.strip().startswith("resynchronizeTurnEncoder"):
                state = 7
        elif state == 4:
            if line.strip().startswith("@Override"):
                state = 3
            else: 
                initialize += line + "\n"
        elif state == 5:
            if line.strip().startswith("@Override"):
                state = 3
            else: 
                setrotation += line + "\n"
        elif state == 6:
            if line.strip().startswith("@Override"):
                state = 3
            else: 
                getrotation += line + "\n"
        elif state == 7:
            if line.strip().startswith("@Override"):
                state = 3
            else: 
                resynchronizeTurnEncoder += line + "\n"

    imports = stripClosedBrakcets(imports).rstrip()
    vars = stripClosedBrakcets(vars).rstrip()
    initialize = stripClosedBrakcets(initialize).rstrip()
    setrotation = stripClosedBrakcets(setrotation).rstrip()
    getrotation = stripClosedBrakcets(getrotation).rstrip()
    resynchronizeTurnEncoder = stripClosedBrakcets(resynchronizeTurnEncoder).rstrip()
    data = {
        "turn_motor_imports": imports,
        "turn_motor_variables": vars,
        "turn_motor_initialize": initialize,
        "turn_motor_set_rotation": setrotation,
        "turn_motor_get_rotation": getrotation,
        "resynchronize_turn_encoder": resynchronizeTurnEncoder
    }

    with open(web_src_folder + "turn/" + filename.replace(".java", ".json"), "w") as file:
        json.dump(data, file, indent=4)

for file in os.listdir(turn_src_folder):
    if file.endswith(".java"):
        scrapeTurnToJson(turn_src_folder + file, file)

##### DRIVE MOTORS #####

def scrapeDriveToJson(srcfile: str, filename: str):
    lines = []
    with open(srcfile, 'r') as file:
        for line in file:
            lines.append(line.rstrip())

    if lines[0] != "//JINJA":
        return
    
    imports = ""
    vars = ""
    initialize = ""
    setspeed = ""
    getposition = ""
    getvelocity = ""

    state = 0

    for line in lines:
        if state == 0:
            if line == "//FILE START":
                state = 1
        elif state == 1:
            if line.strip().startswith("public class"):
                state = 2
            else:
                imports += line + "\n"
        elif state == 2:
            if line.strip().startswith("@Override"):
                state = 3
            else:
                vars += line + "\n"
        elif state == 3:
            subline = line.strip().split(" ")[2]
            if subline.strip().startswith("initialize"):
                state = 4
            elif subline.strip().startswith("setSpeed"):
                state = 5
            elif subline.strip().startswith("getWheelPosition"):
                state = 6
            elif subline.strip().startswith("getWheelVelocity"):
                state = 7
        elif state == 4:
            if line.strip().startswith("@Override"):
                state = 3
            else: 
                initialize += line + "\n"
        elif state == 5:
            if line.strip().startswith("@Override"):
                state = 3
            else: 
                setspeed += line + "\n"
        elif state == 6:
            if line.strip().startswith("@Override"):
                state = 3
            else: 
                getposition += line + "\n"
        elif state == 7:
            if line.strip().startswith("@Override"):
                state = 3
            else:
                getvelocity += line + "\n"

    imports = stripClosedBrakcets(imports).rstrip()
    vars = stripClosedBrakcets(vars).rstrip()
    initialize = stripClosedBrakcets(initialize).rstrip()
    setspeed = stripClosedBrakcets(setspeed).rstrip()
    getposition = stripClosedBrakcets(getposition).rstrip()
    getvelocity = stripClosedBrakcets(getvelocity).rstrip()
    
    data = {
        "drive_motor_imports": imports,
        "drive_motor_variables": vars,
        "drive_motor_initialize": initialize,
        "drive_motor_set_speed": setspeed,
        "drive_motor_get_wheel_position": getposition,
        "drive_motor_get_wheel_velocity": getvelocity
    }

    with open(web_src_folder + "drive/" + filename.replace(".java", ".json"), "w") as file:
        json.dump(data, file, indent=4)

for file in os.listdir(drive_src_folder):
    if file.endswith(".java"):
        scrapeDriveToJson(drive_src_folder + file, file)

##### ENCODERS #####

def scrapeEncoderToJson(srcfile: str, filename: str):
    lines = []
    with open(srcfile, 'r') as file:
        for line in file:
            lines.append(line.rstrip())

    if lines[0] != "//JINJA":
        return
    
    imports = ""
    vars = ""
    initialize = ""
    getrotation = ""

    state = 0

    for line in lines:
        if state == 0:
            if line == "//FILE START":
                state = 1
        elif state == 1:
            if line.strip().startswith("public class"):
                state = 2
            else:
                imports += line + "\n"
        elif state == 2:
            if line.strip().startswith("@Override"):
                state = 3
            else:
                vars += line + "\n"
        elif state == 3:
            subline = line.strip().split(" ")[2]
            if subline.strip().startswith("initialize"):
                state = 4
            elif subline.strip().startswith("getRotations"):
                state = 5
        elif state == 4:
            if line.strip().startswith("@Override"):
                state = 3
            else: 
                initialize += line + "\n"
        elif state == 5:
            if line.strip().startswith("@Override"):
                state = 3
            else: 
                getrotation += line + "\n"

    imports = stripClosedBrakcets(imports).rstrip()
    vars = stripClosedBrakcets(vars).rstrip()
    initialize = stripClosedBrakcets(initialize).rstrip()
    getrotation = stripClosedBrakcets(getrotation).rstrip()
    
    data = {
        "angle_encoder_imports": imports,
        "angle_encoder_variables": vars,
        "angle_encoder_initialize": initialize,
        "angle_encoder_get_rotation": getrotation,
    }

    with open(web_src_folder + "encoder/" + filename.replace(".java", ".json"), "w") as file:
        json.dump(data, file, indent=4)

for file in os.listdir(encoder_src_folder):
    if file.endswith(".java"):
        scrapeEncoderToJson(encoder_src_folder + file, file)

##### GYROS #####

def scrapeGyroToJson(srcfile: str, filename: str):
    lines = []
    with open(srcfile, 'r') as file:
        for line in file:
            lines.append(line.rstrip())

    if lines[0] != "//JINJA":
        return
    
    imports = ""
    vars = ""
    initialize = ""
    getrotation = ""

    state = 0

    for line in lines:
        if state == 0:
            if line == "//FILE START":
                state = 1
        elif state == 1:
            if line.strip().startswith("public class"):
                state = 2
            else:
                imports += line + "\n"
        elif state == 2:
            if line.strip().startswith("@Override"):
                state = 3
            else:
                vars += line + "\n"
        elif state == 3:
            subline = line.strip().split(" ")[2]
            if subline.strip().startswith("initialize"):
                state = 4
            elif subline.strip().startswith("getAngle"):
                state = 5
        elif state == 4:
            if line.strip().startswith("@Override"):
                state = 3
            else: 
                initialize += line + "\n"
        elif state == 5:
            if line.strip().startswith("@Override"):
                state = 3
            else: 
                getrotation += line + "\n"

    imports = stripClosedBrakcets(imports).rstrip()
    vars = stripClosedBrakcets(vars).rstrip()
    initialize = stripClosedBrakcets(initialize).rstrip()
    getrotation = stripClosedBrakcets(getrotation).rstrip()
    
    data = {
        "gyro_imports": imports,
        "gyro_variables": vars,
        "gyro_initialize": initialize,
        "gyro_get_rotation": getrotation,
    }

    with open(web_src_folder + "gyro/" + filename.replace(".java", ".json"), "w") as file:
        json.dump(data, file, indent=4)

for file in os.listdir(gyro_src_folder):
    if file.endswith(".java"):
        scrapeGyroToJson(gyro_src_folder + file, file)