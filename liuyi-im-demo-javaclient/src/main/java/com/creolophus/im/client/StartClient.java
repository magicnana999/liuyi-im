package com.creolophus.im.client;

import java.awt.EventQueue;

/**
 * @author magicnana
 * @date 2020/7/30 下午5:52
 */
public class StartClient {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MyClientWindow frame=new MyClientWindow();
                frame.setVisible(true);
//                    ConnectionManager.getChatManager().setWindow(frame);
            }
            catch (Exception e) {
            }
        });
    }
}
