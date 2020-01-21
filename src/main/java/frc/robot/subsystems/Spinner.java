/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.TestingDashboard;

public class Spinner extends SubsystemBase {
  public static Spinner spinner;

  /**
   * Creates a new Spinner.
   */
  private Spinner() {

  }

  public static Spinner getInstance() {
    if (spinner == null) {
      spinner = new Spinner();
      TestingDashboard.getInstance().registerSubsystem(spinner, "Spinner");
    }
    return spinner;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
