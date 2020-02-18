/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// Spins the conveyor motor for a specified amount of time.
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.BallIntake;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpinBothConveyorsTimed extends CommandBase {
  /**
   * Creates a new SpinConveyor1Timed.
   */

   Timer m_timer;
   BallIntake m_ballIntake;
   double m_Vperiod, m_Hperiod;

  public SpinBothConveyorsTimed() {
    // Use addRequirements() here to declare subsystem dependencies.
    
    addRequirements(BallIntake.getInstance());
    m_timer = new Timer();
    m_ballIntake = BallIntake.getInstance();

  }

  public static void registerWithTestingDashboard() {
    BallIntake ballIntake = BallIntake.getInstance();
    SpinBothConveyorsTimed cmd = new SpinBothConveyorsTimed();
    TestingDashboard.getInstance().registerCommand(ballIntake, "Timed", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_Vperiod = SmartDashboard.getNumber("ConveyorVMotoryTimeout", 5); // default of 5 seconds
    m_Hperiod = SmartDashboard.getNumber("ConveyorHMotoryTimeout", 5); // default of 5 seconds
    m_timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double vSpeed = SmartDashboard.getNumber("ConveyorVMotorSpeed",0.5);
    double hSpeed = SmartDashboard.getNumber("ConveyorHMotorSpeed",0.5);

    m_ballIntake.spinBothConveyors(hSpeed, vSpeed);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_ballIntake.spinBothConveyors(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean vTimerExpired = m_timer.hasPeriodPassed(m_Vperiod);
    boolean hTimerExpired = m_timer.hasPeriodPassed(m_Hperiod);
    return (vTimerExpired || hTimerExpired);
  }
}