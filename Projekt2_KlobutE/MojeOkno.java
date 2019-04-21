import java.io.*;
import javax.imageio.*;

import java.awt.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class MojeOkno extends JFrame implements ActionListener
{
    //Tworzymy panele okna głównego
    PanelGraficzny lewy = new PanelGraficzny();
    PanelGraficzny prawy = new PanelGraficzny();  
    PanelGraficzny dolny_l=new PanelGraficzny();
    PanelGraficzny dolny_p=new PanelGraficzny();
    //Tworzymy menu okna 
    MenuOkna menu = new MenuOkna();
    //scieżka dostępu wraz z nazwą pliku
    String sciezkaDoPlik;  
    
    //Konstruktor
    public MojeOkno() {
        //wywolanie konstruktora klasy nadrzednej (JFrame)
        super("Projekt drugi - histogram RGB Klobut Ewelina");
        //ustawienie standardowej akcji po naciśnięciu przycisku zamkniecia
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //blokada zmiany rozmiaru okna
        setResizable (true);
        //rozmieszczenie elementow - menadzer rozkladu
        
        //setLayout(new FlowLayout());
        setLayout(new GridLayout(2, 2));
        //setLayout(new FlowLayout(FlowLayout.CENTER, 3, 2));
        //setSize(200, 700);
	//setLocation(50,50);
	//ustawienie okna na max wielkosc
	//setExtendedState(JFrame.MAXIMIZED_BOTH); 
	//setUndecorated(true);
        //ustawienie stworzonego menu
        setJMenuBar(menu);
       
        //dodanie paneli
        add(lewy);
        add(prawy);
        add(dolny_l);
        add(dolny_p);
        
        //przypisanie obsługi akcji
        ustawNasluchZdarzen();        
        dopasujSieDoZawartosci();
        //wyswietlenie naszej ramki
        setVisible(true);
    }

    private void ustawNasluchZdarzen()
    //czyli kto kogo podsłuchuje
    {
        menu.otworzPlik.addActionListener(this); 
        menu.zapiszPlik.addActionListener(this); 
        menu.zakoncz.addActionListener(this);  
        
        menu.rysujHist.addActionListener(this); 
        menu.WyczyscLewy.addActionListener(this);
        menu.WyczyscPrawy.addActionListener(this);  
        
        menu.rysHistWyrownany.addActionListener(this); 
        menu.obrazDocelowy.addActionListener(this);
        
        menu.dystrybRozjas.addActionListener(this);
        menu.dystrybuantaKontra.addActionListener(this);
        menu.dystrybuantaZaciem.addActionListener(this);
        menu.dystrybuantaWyrow.addActionListener(this);
    }
    //METODY OBSŁUGI ZDARZEŃ DLA INTERFEJSÓW
    //ActionListener
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //pobieramy etykietę z przycisku
        String label = e.getActionCommand();
        if(label.equals("Otworz plik"))
        {
            otworzPlik();
        }      
        else if(label.equals("Zapisz plik"))
        {
            zapiszPlik();
        } 
        else if(label.equals("Zakończ"))
        {
            System.exit(0);
        }    
        else if(label.equals("Wyczyść lewy"))
        {
            lewy.wyczysc();       
        }      
        else if(label.equals("Rysuj histogram"))
        {
           int w = prawy.plotno.getWidth();
            int h = prawy.plotno.getHeight();            
          
            dolny_l.paintImg(lewy.plotno);
          
        }           
        else if(label.equals("Wyczyść prawy"))
        {
            prawy.wyczysc();          
        } 
        
        else if(label.equals("Rysuj histogram wyrównany"))
        {
            int w = prawy.plotno.getWidth();
            int h = prawy.plotno.getHeight();            
           
            dolny_p.paintImg2(lewy.plotno);
                      
        } 
        else if(label.equals("Obraz docelowy"))
        {
            int w = prawy.plotno.getWidth();
            int h = prawy.plotno.getHeight();            
        
              prawy.histogramWyrownany(lewy.plotno);   
        }
        
        else if(label.equals("Dystrybuanta rozjasnienie"))
        {
            int w = prawy.plotno.getWidth();
            int h = prawy.plotno.getHeight(); 
            
            prawy.dystrybuantaDomyslna(lewy.plotno, "rozjasniajaca.txt");
                      
        }
        else if(label.equals("Dystrybuanta kontrastujaca"))
        {
            int w = prawy.plotno.getWidth();
            int h = prawy.plotno.getHeight(); 
            
            prawy.dystrybuantaDomyslna(lewy.plotno, "kontrastujaca.txt");
                      
        }
        else if(label.equals("Dystrybuanta zaciemniajaca"))
        {
            int w = prawy.plotno.getWidth();
            int h = prawy.plotno.getHeight(); 
            
            prawy.dystrybuantaDomyslna(lewy.plotno, "zaciemniajaca.txt");
                      
        }
        else if(label.equals("Dystrybuanta wyrownujaca"))
        {
            int w = prawy.plotno.getWidth();
            int h = prawy.plotno.getHeight(); 
            
            prawy.dystrybuantaDomyslna(lewy.plotno, "wyrownujaca.txt");
                      
        }
    }   

    //akcja w przypadku wyboru "otwórz plik z menu"
    private void otworzPlik()
    {
        //okno dialogowe do wyboru pliku graficznego
        JFileChooser otworz= new JFileChooser();            
        //zdefiniowanie filtra dla wybranych typu plików
        FileNameExtensionFilter filtr = new FileNameExtensionFilter("JPG & BMP & PNG Images", "jpg", "bmp", "png");
        //ustawienie filtra dla JFileChooser
        otworz.setFileFilter(filtr);
        //wyświetlenie okna dialogowego wyboru pliku
        int wynik = otworz.showOpenDialog(this);
        //analiza rezultatu zwróconego przez okno dialogowe
        if (wynik == JFileChooser.APPROVE_OPTION)   
        {
            //wyłuskanie ścieżki do wybranego pliku
            sciezkaDoPlik= otworz.getSelectedFile().getPath();    
            int w = lewy.plotno.getWidth();
            int h = lewy.plotno.getHeight();
            //wczytanie pliku graficznego na lewy panel
            lewy.wczytajPlikGraficzny(sciezkaDoPlik);  
            if(w != lewy.plotno.getWidth() || h != lewy.plotno.getHeight())
               dopasujSieDoZawartosci();
        }
    } 

    private void zapiszPlik()
    {
        //okno dialogowe do wyboru pliku graficznego
        JFileChooser zapisz;
        //otwarcie JFileChoosera w tym samym katalogu, z którego wczytano plik
        if(sciezkaDoPlik != null)
            zapisz = new JFileChooser(sciezkaDoPlik);    
        else
            zapisz = new JFileChooser();
        //zdefiniowanie filtra dla wybranych typu plików
        FileNameExtensionFilter filtr = new FileNameExtensionFilter("JPG & BMP & PNG Images", "jpg", "bmp", "png");
        //ustawienie filtra dla JFileChooser
        zapisz.setFileFilter(filtr);
        //wyświetlenie okna dialogowego wyboru pliku
        int wynik = zapisz.showSaveDialog(this);      
        //analiza rezultatu zwróconego przez okno dialogowe
        if (wynik == JFileChooser.APPROVE_OPTION)   
        {
            //wyłuskanie ścieżki do wybranego pliku
            sciezkaDoPlik= zapisz.getSelectedFile().getPath();    
            //JOptionPane.showMessageDialog(null,"Blad odczytu pliku: " + sciezkaDoPlik); 
            prawy.zapiszPlikGraficzny(sciezkaDoPlik);
        }        
    }

    private void dopasujSieDoZawartosci()
    {
        //dostosowanie okna do zawartości
        pack();   
        //wyśrodkowanie ramki
        setLocationRelativeTo(null);           
    }
}
