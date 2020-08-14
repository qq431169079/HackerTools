package com.HackerTools.WebTools;

import com.HackerTools.WebTools.IP.getIP;
import com.sun.istack.internal.Nullable;
import org.apache.commons.lang.ObjectUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import javax.swing.JFrame;

/**
 * 程序主类，负责UI配置。
 *
 * @author XiaoLi8848
 */
public class WebToolsManager {
    static double size = 1.0;  //主窗体及控件大小缩放倍数
    static int frame_width = (int) (1000 * size);   //主窗体默认宽度
    static int frame_height = (int) (1000 * size);  //主窗体默认高度
    static String frame_Title = "WebToolsManger";   //窗体标题
    static Font font_TitleLabel = new Font("宋体", Font.PLAIN, 30);  //标题字体
    static Font font_Label = new Font("宋体", Font.PLAIN, 25);  //标签字体
    static Font font_TextField = new Font("宋体", Font.PLAIN, 28); //文本框字体
    static JFrame main_Frame = new JFrame(frame_Title); //创建主窗体
    static JTextArea LogText = new JTextArea(7, 32);    //创建日志文本框

    public static void main(String[] args) {
        main_Frame.setLayout(null); //使用绝对布局器
        main_Frame.setSize(frame_width, frame_height);
        main_Frame.setBackground(Color.WHITE);
        main_Frame.setResizable(false); //禁止最大化或拉伸窗体

        //创建程序标题标签
        JLabel titleLabel = new JLabel(frame_Title, JLabel.CENTER);    //标题文字
        titleLabel.setFont(font_TitleLabel);
        titleLabel.setBounds((frame_width / 2) - 150, 15, (int) (300 * size), (int) (40 * size));
        main_Frame.add(titleLabel);

        //创建IP地址框输入标签
        JLabel ipTextFieldLabel = new JLabel("IP/HostName/URL :");
        ipTextFieldLabel.setFont(font_Label);
        ipTextFieldLabel.setBounds(15, 60, 250, 50);
        main_Frame.add(ipTextFieldLabel);

        //创建IP地址输入框
        JTextField ipTextField = new JTextField("http://www.baidu.com");
        ipTextField.setFont(font_TextField);
        ipTextField.setBounds(240, 70, (int) (350 * size), (int) (30 * size));
        main_Frame.add(ipTextField);

        //创建IP地址显示标签
        JLabel ipSetLabel = new JLabel("IP :");
        ipSetLabel.setFont(font_Label);
        ipSetLabel.setBounds(15, 100, 200, 50);
        main_Frame.add(ipSetLabel);

        //创建IP地址标签
        JLabel ipLabel = null;
        try {
            ipLabel = new JLabel(new getIP().getIP_byURL(ipTextField.getText()).getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ipLabel.setFont(font_Label);
        ipLabel.setBounds(65, 100, 200, 50);
        main_Frame.add(ipLabel);

        //创建IP地理位置显示标签
        JLabel ipLocSetLabel = new JLabel("IPLoc :");
        ipLocSetLabel.setFont(font_Label);
        ipLocSetLabel.setBounds(15, 140, 200, 50);
        main_Frame.add(ipLocSetLabel);

        //创建IP地理位置标签
        JLabel ipLocLabel = null;
        try {
            ipLocLabel = new JLabel(new getIP().getIPLoc(new getIP().getIP_byURL(ipTextField.getText()).getHostAddress()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ipLocLabel.setFont(font_Label);
        ipLocLabel.setBounds(100, 140, 400, 50);
        main_Frame.add(ipLocLabel);

        //创建IP地址解析按钮
        JButton ipParseButton = new JButton("Parse");
        ipParseButton.setFont(font_Label);
        ipParseButton.setBounds(620, 70, (int) (100 * size), (int) (30 * size));
        JLabel finalIpLabel = ipLabel;
        JLabel finalIpLocLabel = ipLocLabel;
        ipParseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    finalIpLabel.setText(new getIP().getIP_byURL(ipTextField.getText()).getHostAddress());
                    finalIpLocLabel.setText(new getIP().getIPLoc(new getIP().getIP_byURL(ipTextField.getText()).getHostAddress()));
                    ParseWebsite(finalIpLabel.getText());
                } catch (UnknownHostException unknownHostException) {
                    unknownHostException.printStackTrace();
                }
            }
        });
        ipLabel.setText(finalIpLabel.getText());
        ipLocLabel.setText(finalIpLocLabel.getText());
        main_Frame.add(ipParseButton);

        LogText.setFont(font_Label);
        LogText.setBounds(15, 400, 965, 500);
        LogText.setLineWrap(true);
        main_Frame.add(LogText);
        main_Frame.setVisible(true); //显示主窗体

        Date date = new Date();
        String time_now = dateToString(date);
        LogText.append('[' + time_now + ']' + " " + "主窗体加载完毕");

    }

    private static void ParseWebsite(String url) {
        try {
            InetAddress ip = InetAddress.getByName(url);
            if (new com.HackerTools.WebTools.WebTools.com.HackerTools.WebTools.PHPInfo().Judge(new com.HackerTools.WebTools.WebToolsAPI(ip))) {
                new com.HackerTools.WebTools.WebTools.com.HackerTools.WebTools.PHPInfo().Attack(new com.HackerTools.WebTools.WebToolsAPI(ip));
            }
            if (new com.HackerTools.WebTools.WebTools.com.HackerTools.WebTools.VCS().Judge(new com.HackerTools.WebTools.WebToolsAPI(ip))) {
                new com.HackerTools.WebTools.WebTools.com.HackerTools.WebTools.VCS().Attack(new com.HackerTools.WebTools.WebToolsAPI(ip));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void Log_Append(int type, String text) {
        Date date = new Date();
        String time_now = dateToString(date);
        LogText.append("\n");

        switch (type) {
            case 1:
                LogText.append('[' + time_now + ']' + " Info: " + text);
                break;
            case 2:
                LogText.append('[' + time_now + ']' + " Error: " + text);
                break;
            case 3:
                LogText.append('[' + time_now + ']' + " Warning: " + text);
                break;
            case 4:
                LogText.append('[' + time_now + ']' + " Succeed: " + text);
                break;
            case 5:
                LogText.append('[' + time_now + ']' + " Fail: " + text);
                break;
            case 0:
            default:
                LogText.append('[' + time_now + ']' + " " + text);
                break;
        }
    }

    public static void Log_Append(String text) {
        Log_Append(0, text);
    }

    private static String dateToString(Date date) {
        String str = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(str);
        String dateFormat = format.format(date);
        return dateFormat;
    }


}
