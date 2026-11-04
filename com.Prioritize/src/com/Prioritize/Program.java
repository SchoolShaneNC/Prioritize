package com.Prioritize;

import javax.swing.SwingUtilities;

import com.Prioritize.View.*;

//starts the Priority application
public class Program
{
  public static void main(String[] args)
  {
      SwingUtilities.invokeLater(() ->
      {
          MainFrame mainFrame = new MainFrame();
          mainFrame.setVisible(true);
      });
  }
}