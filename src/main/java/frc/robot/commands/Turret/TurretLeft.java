/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Turret;

public class TurretLeft extends CommandBase {
  private Turret m_turret;
  
  /**
   * Creates a new TurretLeft.
   */
  public TurretLeft() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_turret);
    m_turret = Turret.getInstance();
  }

  public static void registerWithTestingDashboard() {
    Turret turret = Turret.getInstance();
    TurretLeft cmd = new TurretLeft();
    TestingDashboard.getInstance().registerCommand(turret, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = SmartDashboard.getNumber("IncrementTurretMotorSpeed", 0.5);
    m_turret.spinTurretMotor(-speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_turret.spinTurretMotor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
