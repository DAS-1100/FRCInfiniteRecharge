// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Drive;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PIDTurnAngle extends PIDCommand {
  static Drive m_drive;
  double m_setpoint;
  boolean m_parameterized;
  double m_initialAngle;
  double m_finalAngle;

  /** Creates a new PIDTurnAngle. */
  public PIDTurnAngle(double setpoint, boolean parameterized) {
    super(
        // The controller that the command will use
        new PIDController(.067, .02, .1),
        // This should return the measurement
        () -> m_drive.getYaw(),
        // This should return the setpoint (can also be a constant)
        () -> setpoint,
        // This uses the output
        output -> {
          // Use the output here
          if (output > .7) {
            output = .7;
          }
          if (output < -.7) {
            output = -.7;
          }
            
          m_drive.tankDrive(output, -output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    addRequirements(Drive.getInstance());
    getController().setTolerance(5);
    getController().enableContinuousInput(-180, 180);
    m_setpoint = setpoint;
    m_parameterized = parameterized;
    m_drive = Drive.getInstance();
    m_initialAngle = 0;
    m_finalAngle = 0;
  }

  public static void registerWithTestingDashboard() {

    Drive drive = Drive.getInstance();
    PIDTurnAngle cmd = new PIDTurnAngle(90,true);
    TestingDashboard.getInstance().registerCommand(drive, "Basic", cmd);
    TestingDashboard.getInstance().registerSendable(drive, "PIDRotation", "RotatePIDController", cmd.getController());
  }

  @Override
  public void initialize() {
    super.initialize();
    m_initialAngle = m_drive.getYaw();
    if (m_parameterized) {
      m_setpoint = TestingDashboard.getInstance().getNumber(m_drive, "AngleToTurnInDegrees");
      TestingDashboard.getInstance().updateNumber(m_drive, "InitialAngle", m_initialAngle);
    }
  }

  @Override
  public void execute() {
    if (m_parameterized) {
      m_setpoint = TestingDashboard.getInstance().getNumber(m_drive, "AngleToTurnInDegrees");
    }
    updateFinalAngle();
    m_useOutput.accept(
        m_controller.calculate(m_measurement.getAsDouble(), m_finalAngle));
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean finished = getController().atSetpoint();
    System.out.println("m_finalAngle: " + m_finalAngle + " Finished: " + finished);
    return finished;
  }

  public void updateFinalAngle() {

    m_finalAngle = m_initialAngle + m_setpoint;
    if (m_finalAngle > 180) {
      double delta = m_setpoint - (180-m_initialAngle);
      m_finalAngle = -180 + delta;
    } else if (m_finalAngle < -180) {
      double delta = m_setpoint - (-180-m_initialAngle);
      m_finalAngle = 180 + delta;
    }
  }

}
