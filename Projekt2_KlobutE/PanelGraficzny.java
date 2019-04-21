import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javafx.scene.chart.*;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import java.util.ArrayList;
import java.util.*;

public class PanelGraficzny extends JPanel
{
    //obiekt do przechowywania grafiki
    BufferedImage plotno;
    Graphics g;
    //konstruktor
    public PanelGraficzny() 
    {
        super();   

        ustawRozmiar(new Dimension(600,300));
        wyczysc();
    }
    //wczytanie obrazka z pliku
    public void wczytajPlikGraficzny(String sciezka) 
    {
        //obiekt reprezentujący plik graficzny o podanej ścieżce
        File plikGraficzny = new File(sciezka);
        //próba odczytania pliku graficznego do bufora
        try {
            plotno = ImageIO.read(plikGraficzny);  
            //odczytanie rozmiaru obrazka
            Dimension rozmiar = new Dimension(plotno.getWidth(), plotno.getHeight());        
            //ustalenie rozmiaru panelu zgodnego z rozmiarem obrazka
            //setPreferredSize(rozmiar);
            //setMaximumSize(rozmiar);
            //ustalenie obramowania
            setBorder(BorderFactory.createLineBorder(Color.black));    
            repaint();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Blad odczytu pliku: " + sciezka); 
            e.printStackTrace();
        }
    }  

    public void zapiszPlikGraficzny(String sciezka)
    {
        //obiekt reprezentujący plik graficzny o podanej ścieżce
        File plikGraficzny = new File(sciezka); 
        //próba zapisania pliku graficznego z bufora
        try {
            if(plotno != null)
            {
                if(!ImageIO.write(plotno, sciezka.substring(sciezka.lastIndexOf('.') + 1), new File(sciezka)))
                {
                    JOptionPane.showMessageDialog(null,"Nie udało sie zapisać pliku w " + sciezka);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Brak obrazu do zapisania");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Nie udało sie zapisać pliku w " + sciezka);
        } 
    }

    public void wyczysc()
    {
        //wyrysowanie białego tła
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, plotno.getWidth(), plotno.getHeight());
        //ustalenie obramowania
        setBorder(BorderFactory.createLineBorder(Color.black)); 
        repaint();
    }

    public void ustawRozmiar(Dimension r)
    {
        plotno = new BufferedImage((int)r.getWidth(), (int)r.getHeight(), BufferedImage.TYPE_INT_RGB);
        setPreferredSize(r);     
        setMaximumSize(r);
    }

    public ArrayList<double[]> wyliczHist(BufferedImage wejscie)
    {

        double Rfreq[]=new double[256];
        double Gfreq[]=new double[256];
        double Bfreq[]=new double[256];

        for(int i=0; i<256; i++)
        {
            Rfreq[i]=0;
            Gfreq[i]=0;
            Bfreq[i]=0;
        }
        int dzielnik=wejscie.getWidth()*wejscie.getHeight();
        Color ci;
        for(int i=0;i<wejscie.getWidth();i++) {
            for(int j=0;j<wejscie.getHeight();j++) {

                ci = new Color(wejscie.getRGB(i,j)); 
                int r =ci.getRed();
                int g = ci.getGreen();
                int b =ci.getBlue();

                Rfreq[r]+=1;
                Gfreq[g]+=1;
                Bfreq[b]+=1;

            }
        }

        for(int i=0;i<256;i++) 
        {
            Rfreq[i]=Rfreq[i]/dzielnik;
            Gfreq[i]=Gfreq[i]/dzielnik;
            Bfreq[i]=Bfreq[i]/dzielnik;
        }

        for(int i=1;i<256;i++) 
        {
            Rfreq[i]=Rfreq[i]+Rfreq[i-1];
            Gfreq[i]=Gfreq[i]+Gfreq[i-1];
            Bfreq[i]=Bfreq[i]+Bfreq[i-1];

        }

        ArrayList<double[]> histogram= new ArrayList<double[]>();
        histogram.add(Rfreq);
        histogram.add(Gfreq);
        histogram.add(Bfreq);

        return histogram;
    }

    public void paintImg(BufferedImage wejscie)
    {
        Graphics gr=plotno.getGraphics();;
        int w=300;
        int h=250;
        int R[]=new int[256];
        int G[]=new int[256];
        int B[]=new int[256];
        ArrayList<double[]> hist=wyliczHist(wejscie);
        for(int i=0; i<256; i++)
        {
            R[i]=(int)(hist.get(0)[i]*200);
            G[i]=(int)(hist.get(1)[i]*200);
            B[i]=(int)(hist.get(2)[i]*200);

        }
        super.paint(gr);
        Graphics2D g2D=(Graphics2D)gr;

        for(int i=1;i<256;i++) {
            //System.out.print(hist.get(0)[i]);
            g2D.setColor(Color.RED);
            g2D.drawLine(20+i,h-R[i-1],20+i,h-R[i]);

            g2D.setColor(Color.GREEN);
            g2D.drawLine(20+i,h-G[i-1],20+i,h-G[i]);
            g2D.setColor(Color.BLUE);
            g2D.drawLine(20+i,h-B[i-1],20+i,h-B[i]);

        } 
        gr.setColor(Color.BLACK);
        g2D.drawLine(21,h-5,21,h-256);
        g2D.drawLine(21,h-3,280,h-3);
        g2D.drawString("0-->255",260,h-5);
        g2D.setFont(new Font("Aril",Font.BOLD,12));

        repaint();
        
    }

    public void paintImg2(BufferedImage wejscie)
    {
        Graphics gr=plotno.getGraphics();;
        int w=300;
        int h=250;
        int R[]=new int[256];
        int G[]=new int[256];
        int B[]=new int[256];
        ArrayList<double[]> hist=wyliczHist(wejscie);
        for(int i=0; i<256; i++)
        {
            R[i]=(int)(hist.get(0)[i]*200);
            G[i]=(int)(hist.get(1)[i]*200);
            B[i]=(int)(hist.get(2)[i]*200);
            //System.out.print(R[i]+", ");
        }
        super.paint(gr);
        Graphics2D g2D=(Graphics2D)gr;

        for(int i=1;i<256;i++) {
            //System.out.print(hist.get(0)[i]);
            g2D.setColor(Color.RED);
            g2D.drawLine(21+R[i-1],h-R[i-1],21+R[i],h-R[i]);

            g2D.setColor(Color.GREEN);
            g2D.drawLine(21+G[i-1],h-G[i-1],21+G[i],h-G[i]);
            g2D.setColor(Color.BLUE);
            g2D.drawLine(21+B[i-1],h-B[i-1],20+B[i],h-B[i]);

        } 
        gr.setColor(Color.BLACK);
        g2D.drawLine(21,h-5,21,h-256);
        g2D.drawLine(21,h-3,280,h-3);
        g2D.drawString("0-->255",260,h-5);
        g2D.setFont(new Font("Aril",Font.BOLD,12));
        repaint();
    }

    public ArrayList<int[]> tablicaLUT(BufferedImage wejscie)
    {
        ArrayList<double[]> imageHist=wyliczHist(wejscie);
        ArrayList<int[]>imageLUT=new ArrayList<int[]>();

        int []rHist=new int[256];
        int []gHist=new int[256];
        int []bHist=new int[256];

        for(int i=0; i<rHist.length; i++) 
        {
            if((imageHist.get(0)[i]*255)>255)
                rHist[i]=255;
            else
                rHist[i]=(int)(imageHist.get(0)[i]*255);
        }
        for(int i=0; i<gHist.length; i++) 
        {
            if((imageHist.get(1)[i]*255)>255)
                gHist[i]=255;
            else
                gHist[i]=(int)(imageHist.get(1)[i]*255);
        }
        for(int i=0; i<bHist.length; i++) 
        {
            if((imageHist.get(2)[i]*255)>255)
                bHist[i]=255;
            bHist[i]=(int)(imageHist.get(2)[i]*255);
            
        }
        
        imageLUT.add(rHist);
        imageLUT.add(gHist);
        imageLUT.add(bHist);

        return imageLUT;
    }

    public void histogramWyrownany(BufferedImage wejscie)
    {
        ustawRozmiar(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        int r;
        int g;
        int b;

        Color ci;
        int newPixel=0;
        ArrayList<int[]>histLUT=tablicaLUT(wejscie);

        for(int i=0; i<wejscie.getWidth(); i++)
        {
            for(int j=0; j<wejscie.getHeight(); j++)
            {
                ci=new Color(wejscie.getRGB(i, j));
                r=ci.getRed();
                g=ci.getGreen();
                b=ci.getBlue();

                int red=histLUT.get(0)[r];
                int green=histLUT.get(1)[g];
                int blue=histLUT.get(2)[b];
                //System.out.print(green+", ");
                Color gColor= new Color(red, green, blue);
                plotno.setRGB(i,j,gColor.getRGB());
            }

        }
        repaint();
    }
    
    public void dystrybuantaRozjas(BufferedImage wejscie)
    {
        try{
            File plik = new File("rozjasniajaca.txt");
            Scanner in = new Scanner(plik);
            double[] tab = new double[256];
            ArrayList<double[]> tabRGB=wyliczHist(wejscie);
            int[]lutR = new int[256];
            int[]lutG = new int[256];
            int[]lutB = new int[256];
            int k=0;
            while(in.hasNextLine()){
                tab[k] = Double.parseDouble(in.nextLine());
                
                k++;
            }
            for(int i=0; i<256; i++)
            {
                for(int j=0; j<256; j++)
                {
                    if(tabRGB.get(0)[i]>tab[j])    
                        if(j==255) lutR[i]=j;     //jeśli nie jest na końcu, zamień na j+1, bo albo będzie równa wartość
                        else lutR[i]=j+1;         // albo będzie wartość większa ale o jedną pozycję 

                    if(tabRGB.get(1)[i]>tab[j])    //
                        if(j==255) lutG[i]=j;   //                     
                        else lutG[i]=j+1;

                    if(tabRGB.get(2)[i]>tab[j])
                        if(j==255) lutB[i]=j;
                        else lutB[i]=j+1;
                }
                //System.out.println(lutR[i]);
            }
            ustawRozmiar(new Dimension(wejscie.getWidth(),wejscie.getHeight()));         
            Color kolor;

            for(int i=0; i<wejscie.getWidth(); i++)
            {
                for(int j=0; j<wejscie.getHeight(); j++)
                {
                    kolor = new Color(wejscie.getRGB(i,j));
                    plotno.setRGB(i,j, new Color(lutR[kolor.getRed()], lutG[kolor.getGreen()], lutB[kolor.getBlue()]).getRGB());
                }  
            }
            repaint();
        }catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Blad odczytu pliku: "); 
            e.printStackTrace();
            
            
        }
    }
    
    public void dystrybuantaDomyslna(BufferedImage wejscie, String sciezka)
    {
        try{
            File plik = new File(sciezka);
            Scanner in = new Scanner(plik);
            double[] tab = new double[256];
            ArrayList<double[]> tabRGB=wyliczHist(wejscie);
            int[]lutR = new int[256];
            int[]lutG = new int[256];
            int[]lutB = new int[256];
            int k=0;
            while(in.hasNextLine()){
                tab[k] = Double.parseDouble(in.nextLine());
                
                k++;
            }
            for(int i=0; i<256; i++)
            {
                for(int j=0; j<256; j++)
                {
                    if(tabRGB.get(0)[i]>tab[j])    
                        if(j==255) lutR[i]=j;     //jeśli nie jest na końcu, zamień na j+1, bo albo będzie równa wartość
                        else lutR[i]=j+1;         // albo będzie wartość większa ale o jedną pozycję 

                    if(tabRGB.get(1)[i]>tab[j])    //
                        if(j==255) lutG[i]=j;   //                     
                        else lutG[i]=j+1;

                    if(tabRGB.get(2)[i]>tab[j])
                        if(j==255) lutB[i]=j;
                        else lutB[i]=j+1;
                }
                //System.out.println(lutR[i]);
            }
            ustawRozmiar(new Dimension(wejscie.getWidth(),wejscie.getHeight()));         
            Color kolor;

            for(int i=0; i<wejscie.getWidth(); i++)
            {
                for(int j=0; j<wejscie.getHeight(); j++)
                {
                    kolor = new Color(wejscie.getRGB(i,j));
                    plotno.setRGB(i,j, new Color(lutR[kolor.getRed()], lutG[kolor.getGreen()], lutB[kolor.getBlue()]).getRGB());
                }  
            }
            repaint();
        }catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Blad odczytu pliku: "); 
            e.printStackTrace();
        }
    }
 
    //przesłonięta metoda paintComponent z klasy JPanel do rysowania
    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //wyrysowanie naszego płótna na panelu 
        g2d.drawImage(plotno, 0, 0, this);
    }    

}
