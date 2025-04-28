<div class="max-w">
    <div class="p-2 pl-3">
        <h1 class="text-2xl font-orbitron">
            SWERVE GENERATOR
        </h1>
    </div>
    <hr/>
</div>
<div class="flex gap-1 p-16 p-4 grid-flow-row-dense gap-8">
    <div class="border rounded-md dark:border-nord-4 border-nord-0 px-4 py-2">
        <h2 class="text-2xl text-center">
            Configuration
        </h2>
        <h3 class="text-xl pt-2 mb-1">
            Module Select
        </h3>
        <Select.Root portal={null} onSelectedChange={(v) => {
            v && (selected_module = v.value);
        }}>
            <Select.Trigger class="">
                <Select.Value placeholder="Select a module" />
            </Select.Trigger>
            <Select.Content>
                <Select.Group>
                <Select.Label>Modules</Select.Label>
                    {#each modules as module}
                        <Select.Item value={module.value} label={module.name}
                        >{module.name}</Select.Item
                        >
                    {/each}
                </Select.Group>
            </Select.Content>
            <Select.Input name="selectedModule" />
        </Select.Root>

        <h3 class="text-xl pt-4 mb-1">
            Ratio Select
        </h3>
        <Select.Root portal={null} onSelectedChange={(v) => {
            v && (selected_ratio = v.value);
        }} bind:disabled={module_selected}>
            <Select.Trigger class="">
                <Select.Value placeholder="Select a ratio" />
            </Select.Trigger>
            <Select.Content>
                <Select.Group>
                <Select.Label>Ratios</Select.Label>
                    {#each selected_module.ratios as ratio}
                        <Select.Item value={ratio} label={ratio.name}
                        >{ratio.name}</Select.Item
                        >
                    {/each}
                </Select.Group>
            </Select.Content>
            <Select.Input name="selectedRatio" />
        </Select.Root>

        <h3 class="text-xl pt-4 mb-1">
            Drive Controller Select
        </h3>

        <ToggleGroup.Root variant="outline" type="single" bind:value={selected_drive_name}>
            {#each motors as motor}
                <ToggleGroup.Item value={motor.name}>{motor.name}</ToggleGroup.Item>
            {/each}
        </ToggleGroup.Root>

        <h3 class="text-xl pt-4 mb-1">
            Turn Controller Select
        </h3>

        <ToggleGroup.Root variant="outline" type="single" bind:value={selected_turn_name}>
            {#each motors as motor}
                <ToggleGroup.Item value={motor.name}>{motor.name}</ToggleGroup.Item>
            {/each}
        </ToggleGroup.Root>

        <h3 class="text-xl pt-4 mb-1">
            Encoder Select
        </h3>

        <div class="flex items-center gap-2 pb-2">
            <Checkbox bind:checked={display_attached}/>
            <p>
                Attached Encoder?
            </p>
        </div>

        <ToggleGroup.Root variant="outline" type="single" bind:value={selected_encoder_name}>
            {#each encoders as encoder}
                <ToggleGroup.Item value={encoder.name}>{encoder.name}</ToggleGroup.Item>
            {/each}
        </ToggleGroup.Root>
        
        <h3 class="text-xl pt-4 mb-1">
            Gyro Select
        </h3>

        <ToggleGroup.Root variant="outline" type="single" bind:value={selected_gyro_name}>
            {#each gyros as gyro}
                <ToggleGroup.Item value={gyro.name}>{gyro.name}</ToggleGroup.Item>
            {/each}
        </ToggleGroup.Root>
    </div>
    <div class="grow border rounded-md dark:border-nord-4 border-nord-0 px-2 py-2 w-full text-xs">
        <Tabs.Root value="SwerveModule" class="">
            <Tabs.List class="grid w-full grid-cols-3">
                <Tabs.Trigger value="SwerveModule">SwerveModule.java</Tabs.Trigger>
                <Tabs.Trigger value="Swerve">Swerve.java</Tabs.Trigger>
                <Tabs.Trigger value="Constants">Constants.java</Tabs.Trigger>
            </Tabs.List>
            <Tabs.Content value="SwerveModule">
                <Highlight language={java} bind:code={swervemodule_code} let:highlighted>
                    <LineNumbers {highlighted} wrapLines />
                </Highlight>
            </Tabs.Content>
            <Tabs.Content value="Swerve">
                <Highlight language={java} bind:code={swerve_code} let:highlighted>
                    <LineNumbers {highlighted} wrapLines />
                </Highlight>
            </Tabs.Content>
            <Tabs.Content value="Constants">
                <Highlight language={java} bind:code={constants_code} let:highlighted>
                    <LineNumbers {highlighted} wrapLines />
                </Highlight>
            </Tabs.Content>
        </Tabs.Root>
    </div>
</div>

<style>
</style>

<svelte:head>
  {@html darcula}
</svelte:head>

<script lang="ts">
    import * as Select from "$lib/components/ui/select/index.js";
    import * as ToggleGroup from "$lib/components/ui/toggle-group";
    import { Checkbox } from "$lib/components/ui/checkbox";
    import nunjucks from "nunjucks";
    import Highlight, { LineNumbers } from "svelte-highlight";
    import darcula from "svelte-highlight/styles/darcula";
    import java from "svelte-highlight/languages/java";
    import * as Tabs from "$lib/components/ui/tabs/index.js";

    import { Encoder, Gyro, Motor, Ratio, SwerveModule } from "$lib/Constants";
    import { onDestroy, onMount } from "svelte";

    let selected_turn_name = "";

    let motors = [
        { value: new Motor("Spark MAX", "REV", []), name: 'REV Spark MAX' },
        { value: new Motor("Spark FLEX", "REV", []), name: 'REV Spark FLEX' },
        { value: new Motor("TalonFX", "CTRE", []), name: 'CTRE TalonFX' },
    ];

    $: selected_turn = motors.find((motor) => motor.name == selected_turn_name)

    let selected_drive_name = "";

    $: selected_drive = motors.find((motor) => motor.name == selected_drive_name)

    let selected_module: SwerveModule;

    $: module_selected = !selected_module;

    let selected_ratio: Ratio;

    let modules = [
        { value: new SwerveModule("SDS MK4", [
            new Ratio("L1", "(50.0 / 14.0) * (19.0 / 25.0) * (45.0 / 15.0)"),
            new Ratio("L2", "(50.0 / 14.0) * (17.0 / 27.0) * (45.0 / 15.0)"),
            new Ratio("L3", "(50.0 / 14.0) * (16.0 / 28.0) * (45.0 / 15.0)"),
            new Ratio("L4", "(48.0 / 16.0) * (16.0 / 28.0) * (45.0 / 15.0)")
        ], "12.8"), name: 'SDS MK4' },
        { value: new SwerveModule("SDS MK4i", [
            new Ratio("L1", "(50.0 / 14.0) * (19.0 / 25.0) * (45.0 / 15.0)"),
            new Ratio("L2", "(50.0 / 14.0) * (17.0 / 27.0) * (45.0 / 15.0)"),
            new Ratio("L3", "(50.0 / 14.0) * (16.0 / 28.0) * (45.0 / 15.0)"),
        ], "150.0/7.0"), name: 'SDS MK4i' },
        { value: new SwerveModule("SDS MK4c", [
            new Ratio("L1", "(50.0 / 16.0) * (19.0 / 25.0) * (45.0 / 15.0)"),
            new Ratio("L2", "(50.0 / 16.0) * (17.0 / 27.0) * (45.0 / 15.0)"),
            new Ratio("L3", "(50.0 / 16.0) * (16.0 / 28.0) * (45.0 / 15.0)"),
        ], "12.8"), name: 'SDS MK4c' },
        { value: new SwerveModule("SDS MK4n", [
            new Ratio("L1", "(50.0 / 16.0) * (19.0 / 25.0) * (45.0 / 15.0)"),
            new Ratio("L2", "(50.0 / 16.0) * (17.0 / 27.0) * (45.0 / 15.0)"),
            new Ratio("L3", "(50.0 / 16.0) * (16.0 / 28.0) * (45.0 / 15.0)"),
        ], "18.75"), name: 'SDS MK4n' },
        { value: new SwerveModule("WCP SwerveX1", [
            new Ratio("X1 (10 tooth pinion)", "(34.0 / 10.0) * (20.0 / 26.0) * (45.0 / 15.0)"),
            new Ratio("X1 (11 tooth pinion)", "(34.0 / 11.0) * (20.0 / 26.0) * (45.0 / 15.0)"),
            new Ratio("X1 (12 tooth pinion)", "(34.0 / 12.0) * (20.0 / 26.0) * (45.0 / 15.0)"),
            new Ratio("X2 (10 tooth pinion)", "(34.0 / 10.0) * (18.0 / 28.0) * (45.0 / 15.0)"),
            new Ratio("X2 (11 tooth pinion)", "(34.0 / 11.0) * (18.0 / 28.0) * (45.0 / 15.0)"),
            new Ratio("X2 (12 tooth pinion)", "(34.0 / 12.0) * (18.0 / 28.0) * (45.0 / 15.0)"),
            new Ratio("X3 (10 tooth pinion)", "(32.0 / 10.0) * (18.0 / 28.0) * (45.0 / 15.0)"),
            new Ratio("X3 (11 tooth pinion)", "(32.0 / 11.0) * (18.0 / 28.0) * (45.0 / 15.0)"),
            new Ratio("X3 (12 tooth pinion)", "(32.0 / 12.0) * (18.0 / 28.0) * (45.0 / 15.0)"),
        ], "((396.0 / 35.0) / 1.0)"), name: 'WCP SwerveX1' },
        { value: new SwerveModule("WCP SwerveX1 (Flipped)", [
            new Ratio("X1 (10 tooth pinion)", "(42.0 / 10.0) * (18.0 / 28.0) * (45.0 / 15.0)"),
            new Ratio("X1 (11 tooth pinion)", "(42.0 / 11.0) * (18.0 / 28.0) * (45.0 / 15.0)"),
            new Ratio("X1 (12 tooth pinion)", "(42.0 / 12.0) * (18.0 / 28.0) * (45.0 / 15.0)"),
            new Ratio("X2 (10 tooth pinion)", "(42.0 / 10.0) * (16.0 / 30.0) * (45.0 / 15.0)"),
            new Ratio("X2 (11 tooth pinion)", "(42.0 / 11.0) * (16.0 / 30.0) * (45.0 / 15.0)"),
            new Ratio("X2 (12 tooth pinion)", "(42.0 / 12.0) * (16.0 / 30.0) * (45.0 / 15.0)"),
            new Ratio("X3 (10 tooth pinion)", "(42.0 / 10.0) * (14.0 / 32.0) * (45.0 / 15.0)"),
            new Ratio("X3 (11 tooth pinion)", "(42.0 / 11.0) * (14.0 / 32.0) * (45.0 / 15.0)"),
            new Ratio("X3 (12 tooth pinion)", "(42.0 / 12.0) * (14.0 / 32.0) * (45.0 / 15.0)"),
        ], "((468.0 / 35.0) / 1.0)"), name: 'WCP SwerveX1 (Flipped)' },
        { value: new SwerveModule("WCP SwerveX2", [
            new Ratio("X1 (10 tooth pinion)", "(54.0 / 10.0) * (18.0 / 38.0) * (45.0 / 15.0)"),
            new Ratio("X1 (11 tooth pinion)", "(54.0 / 11.0) * (18.0 / 38.0) * (45.0 / 15.0)"),
            new Ratio("X1 (12 tooth pinion)", "(54.0 / 12.0) * (18.0 / 38.0) * (45.0 / 15.0)"),
            new Ratio("X2 (10 tooth pinion)", "(54.0 / 10.0) * (16.0 / 38.0) * (45.0 / 15.0)"),
            new Ratio("X2 (11 tooth pinion)", "(54.0 / 11.0) * (16.0 / 38.0) * (45.0 / 15.0)"),
            new Ratio("X2 (12 tooth pinion)", "(54.0 / 12.0) * (16.0 / 38.0) * (45.0 / 15.0)"),
            new Ratio("X3 (10 tooth pinion)", "(54.0 / 10.0) * (16.0 / 40.0) * (45.0 / 15.0)"),
            new Ratio("X3 (11 tooth pinion)", "(54.0 / 11.0) * (16.0 / 40.0) * (45.0 / 15.0)"),
            new Ratio("X3 (12 tooth pinion)", "(54.0 / 12.0) * (16.0 / 40.0) * (45.0 / 15.0)"),
            new Ratio("X4 (10 tooth pinion)", "(54.0 / 10.0) * (14.0 / 40.0) * (45.0 / 15.0)"),
            new Ratio("X4 (11 tooth pinion)", "(54.0 / 11.0) * (14.0 / 40.0) * (45.0 / 15.0)"),
            new Ratio("X4 (12 tooth pinion)", "(54.0 / 12.0) * (14.0 / 40.0) * (45.0 / 15.0)"),
        ], "12.1"), name: 'WCP SwerveX2' },
        { value: new SwerveModule("WCP SwerveXS2", [
            new Ratio("X1 (15 tooth pinion)", "(30.0 / 15.0) * 3.0"),
            new Ratio("X1 (16 tooth pinion)", "(30.0 / 16.0) * 3.0"),
            new Ratio("X1 (17 tooth pinion)", "(30.0 / 17.0) * 3.0"),
            new Ratio("X2 (15 tooth pinion)", "(28.0 / 15.0) * 3.0"),
            new Ratio("X2 (16 tooth pinion)", "(28.0 / 16.0) * 3.0"),
            new Ratio("X2 (17 tooth pinion)", "(28.0 / 17.0) * 3.0"),
            new Ratio("X3 (15 tooth pinion)", "(26.0 / 15.0) * 3.0"),
            new Ratio("X3 (16 tooth pinion)", "(26.0 / 16.0) * 3.0"),
            new Ratio("X3 (17 tooth pinion)", "(26.0 / 17.0) * 3.0"),
        ], "((468.0 / 35.0) / 1.0)"), name: 'WCP SwerveXS2' },
    ];

    let encoders_master = [
        { value: new Encoder("CANCoder", "CTRE", false, "0", ""), name: 'CANCoder' },
        { value: new Encoder("Canandmag", "RDX", false, "0", ""), name: 'Helium Canandmag' },
    ];

    let encoders_revattached = [
        { value: new Encoder("Canandmag", "RDX", true, "-1", ""), name: 'Helium Canandmag' },
        { value: new Encoder("SRX Mag", "CTRE", true, "-1", ""), name: 'SRX Mag Encoder' },
    ];

    let encoders_ctrattached = [
        { value: new Encoder("CANCoder", "CTRE", true, "0", ""), name: 'CANCoder' },
    ];

    let encoders = encoders_master;

    let display_attached = false;

    $: {
        if(display_attached){
            if(selected_turn?.value.mfg == "REV") {
                encoders = encoders_revattached;
            }
            if(selected_turn?.value.mfg == "CTRE") {
                encoders = encoders_ctrattached;
            }
        }else {
            encoders = encoders_master;
        }
    }

    let selected_encoder_name = "";

    $: selected_encoder = encoders.find((encoder) => encoder.name == selected_encoder_name)

    let gyros = [
        { value: new Gyro("Pigeon2", "CTRE"), name: "Pigeon 2" },
        { value: new Gyro("Canandgyro", "RDX"), name: "Canandgyro" }
    ]

    let selected_gyro_name = "";

    $: selected_gyro = gyros.find((gyro) => gyro.name == selected_gyro_name)

    let swervemodule_code = "";
    let swerve_code = "";
    let constants_code = "";

    async function createSwerveModule(){
        let j2template = await (await fetch("/jinja/SwerveModule.j2")).text();

        let turnmotorobj;

        if(selected_turn?.value.name === "Spark MAX") {
            if(selected_encoder?.value.attached) {
                turnmotorobj = await (await fetch("/jinja/turn_motor/RevSparkMaxTurnMotorAttached.json")).json();
            }else{
                turnmotorobj = await (await fetch("/jinja/turn_motor/RevSparkMaxTurnMotor.json")).json();
            }
        }else if(selected_turn?.value.name === "Spark FLEX") {
            if(selected_encoder?.value.attached) {
                turnmotorobj = await (await fetch("/jinja/turn_motor/RevSparkFlexTurnMotorAttached.json")).json();
            }else{
                turnmotorobj = await (await fetch("/jinja/turn_motor/RevSparkFlexTurnMotor.json")).json();
            }
        }else if(selected_turn?.value.name == "TalonFX") {
            if(selected_encoder?.value.attached) {
                turnmotorobj = await (await fetch("/jinja/turn_motor/CTRETalonFXTurnMotorAttached.json")).json();
            }else{
                turnmotorobj = await (await fetch("/jinja/turn_motor/CTRETalonFXTurnMotor.json")).json();
            }
        }

        let drivemotorobj;

        if(selected_drive?.value.name === "Spark MAX") {
            drivemotorobj = await (await fetch("/jinja/drive_motor/RevSparkMaxDriveMotor.json")).json();
        }else if(selected_drive?.value.name === "Spark FLEX") {
            drivemotorobj = await (await fetch("/jinja/drive_motor/RevSparkFlexDriveMotor.json")).json();
        }else if(selected_drive?.value.name == "TalonFX") {
            drivemotorobj = await (await fetch("/jinja/drive_motor/CTRETalonFXDriveMotor.json")).json();
        }

        if(selected_drive?.value.name === selected_turn?.value.name && drivemotorobj && turnmotorobj) {
            let drivemotorimports = drivemotorobj.drive_motor_imports.split("\n");
            let turnmotorimports = turnmotorobj.turn_motor_imports.split("\n");

            let tmp = "";

            drivemotorimports.forEach((dimp) => {
                let notfound = false;
                turnmotorimports.forEach((timp) => {
                    if(dimp != timp) {
                        notfound = true;
                    }
                });
                if(notfound) {
                    tmp += dimp + "\n";
                }
            });

            drivemotorobj.drive_motor_imports = "";
            drivemotorobj.turn_motor_imports = tmp;
        }

        let encoderobj;

        if(selected_encoder && parseInt(selected_encoder?.value.canid) >= 0){
            if(selected_encoder?.value.name === "CANCoder") {
                encoderobj = await (await fetch("/jinja/angle_encoder/CTRECANCoder.json")).json();
            }else if(selected_encoder?.value.name === "Canandmag") {
                encoderobj = await (await fetch("/jinja/angle_encoder/ReduxCanandmag.json")).json();
            }
        }

        let attached_encoder = selected_encoder?.value.attached;

        swervemodule_code = nunjucks.renderString(j2template, {...turnmotorobj, ...drivemotorobj, ...encoderobj, attached_encoder});
    }

    async function createSwerve(){
        let j2template = await (await fetch("/jinja/Swerve.j2")).text();

        let gyroobj;
        if(selected_gyro?.value.name === "Pigeon2") {
            gyroobj = await (await fetch("/jinja/gyro/CTREPigeon2.json")).json();
        }else if(selected_gyro?.value.name === "Canandgyro") {
            gyroobj = await (await fetch("/jinja/gyro/ReduxCanandgyro.json")).json();
        }

        let attached_encoder = selected_encoder?.value.attached;

        swerve_code = nunjucks.renderString(j2template, {...gyroobj, attached_encoder});
    }

    async function createConstants(){
        let j2template = await (await fetch("/jinja/Constants.j2")).text();

        constants_code = nunjucks.renderString(j2template, {
            trackWidth: 0,
            wheelBase: 0,
            maxSpeed: 0,
            wheelCircumference: selected_module?.wheel_circumference,
            driveRatio: selected_ratio?.value,
            turnRatio: selected_module?.turn_ratio,
            gyroID: 0,
            motorFreeSpeedRps: 100,
            encoderOverCAN: parseInt(selected_encoder ? selected_encoder.value.canid : "-1") >= 0,
            drive_motor_0_can_id: 0,
            turn_motor_0_can_id: 0,
            encoder_0_can_id: 0,
            drive_motor_1_can_id: 0,
            turn_motor_1_can_id: 0,
            encoder_1_can_id: 0,
            drive_motor_2_can_id: 0,
            turn_motor_2_can_id: 0,
            encoder_2_can_id: 0,
            drive_motor_3_can_id: 0,
            turn_motor_3_can_id: 0,
            encoder_3_can_id: 0,
        });
    }

    let recreate_interval: NodeJS.Timeout;

    onMount(() => {
        recreate_interval = setInterval(() => {
            createSwerveModule();
            createSwerve();
            createConstants();
        }, 250);
    });

    onDestroy(() => {
        clearInterval(recreate_interval);
    });
</script>