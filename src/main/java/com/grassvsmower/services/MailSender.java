/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grassvsmower.services;

/**
 *
 * @author Adriana
 */
public interface MailSender {
    
    public boolean send(String from, String to, String subject, String text, final boolean isHtmlMail);
}
