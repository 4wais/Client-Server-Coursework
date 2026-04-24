/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author awais
 */

public class LinkedResourceNotFoundException extends RuntimeException {

    public LinkedResourceNotFoundException() {
        super("Referenced Room does not exist.");
    }
}