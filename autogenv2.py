import os
import json

src_folder = "./BaseProject/src/main/java/frc/robot/autogen/"
web_src_folder = "./SwerveCreator/static/jinja/"

type_folders = {
    "turn_motor": src_folder + "TurnMotor/",
    "drive_motor": src_folder + "DriveMotor/",
    "angle_encoder": src_folder + "Encoder/",
    "gyro": src_folder + "Gyro/"
}

def rreplace(s, old, new, o):
    li = s.rsplit(old, o)
    return new.join(li)

def strip_closed_brackets(s: str):
    count = 0
    for c in s:
        if c == '{':
            count += 1
        elif c == '}':
            count -= 1
    return rreplace(s, "}\n", "", abs(count))

def scrape_to_json(srcfile: str, filename: str, output_folder: str, prefix: str):
    with open(srcfile, 'r') as file:
        lines = [line.rstrip() for line in file]

    if not lines or lines[0] != "//JINJA":
        return
    
    imports = ""
    vars = ""
    methods = {}
    current_method = None
    buffer = ""

    state = 0

    for line in lines:
        if state == 0:
            if line.strip() == "//FILE START":
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
            tokens = line.strip().split()
            if len(tokens) >= 3:
                funcname = tokens[2].split('(')[0]
                current_method = funcname
                methods[current_method] = ""
                state = 4
        elif state == 4:
            if line.strip().startswith("@Override"):
                state = 3
            else:
                methods[current_method] += line + "\n"

    imports = strip_closed_brackets(imports).rstrip()
    vars = strip_closed_brackets(vars).rstrip()
    methods = {name: strip_closed_brackets(body).rstrip() for name, body in methods.items()}

    data = {
        f"{prefix}_imports": imports,
        f"{prefix}_variables": vars,
    }
    for method_name, method_body in methods.items():
        data[f"{prefix}_{method_name.lower()}"] = method_body

    os.makedirs(web_src_folder + output_folder, exist_ok=True)
    with open(os.path.join(web_src_folder, output_folder, filename.replace(".java", ".json")), "w") as file:
        json.dump(data, file, indent=4)

for type_key, folder in type_folders.items():
    for file in os.listdir(folder):
        if file.endswith(".java"):
            scrape_to_json(
                srcfile=os.path.join(folder, file),
                filename=file,
                output_folder=type_key,
                prefix=type_key.rstrip('s')
            )
