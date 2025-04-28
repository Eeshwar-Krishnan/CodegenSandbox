export class Encoder {
    name: String;
    mfg: String;
    attached: boolean;
    canid: string
    bus: string
    constructor(name: String, mfg: String, attached: boolean, canid: string, bus: string){
        this.name = name;
        this.mfg = mfg;
        this.attached = attached;
        this.canid = canid;
        this.bus = bus;
    }

    clone(): Encoder {
        return new Encoder(this.name, this.mfg, this.attached, this.canid, this.bus);
    }
}

export class Ratio {
    name: string;
    value: string;

    constructor(name: string, ratio: string){
        this.name = name + " (" + eval(ratio).toFixed(2) + ":1)";
        this.value = ratio;
    }
}

export class SwerveModule {
    name: String;
    ratios: Ratio[];
    selected_ratio: String = "1";
    turn_ratio: String;
    wheel_circumference: string;

    constructor(name: String, ratios: Ratio[], turn_ratio: String){
        this.name = name;
        this.ratios = ratios;
        this.turn_ratio = turn_ratio;
        this.wheel_circumference = "4.0 * 0.0254 * Math.PI";
    }
}

export class Motor {
    name: String;
    mfg: String;
    encoders: Encoder[];
    canid: String;
    bus: string;

    constructor(name: String, mfg: String, encoders: Encoder[]){
        this.name = name;
        this.mfg = mfg;
        this.encoders = encoders;
        this.canid = "";
        this.bus = "";
    }

    clone(): Motor {
        let m: Motor = new Motor(this.name, this.mfg, this.encoders);
        m.canid = this.canid;
        m.bus = this.bus;
        return m;
    }
}

export class Module {
    name: String;
    driveid: number;
    turnid: number;
    encoderid: number;

    drivebus: String;
    turnbus: String;
    encoderbus: String;

    constructor(name: String, driveid: number, turnid: number, encoderid: number) {
        this.name = name;
        this.driveid = driveid;
        this.turnid = turnid;
        this.encoderid = encoderid;

        this.drivebus = "rio";
        this.turnbus = "rio";
        this.encoderbus = "rio";
    }
}

export class Gyro {
    name: String;
    mfg: String;

    constructor(name: String, mfg: String) {
        this.name = name;
        this.mfg = mfg;
    }
}