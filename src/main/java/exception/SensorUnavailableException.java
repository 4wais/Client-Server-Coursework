/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author awais
 */

public class SensorUnavailableException extends RuntimeException {

    public SensorUnavailableException() {
        super("Sensor is in MAINTENANCE state and cannot accept new readings.");
    }
}