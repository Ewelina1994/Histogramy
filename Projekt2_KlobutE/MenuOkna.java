import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JTextField;
public class MenuOkna extends JMenuBar
{
    JMenu plik = new JMenu("Plik");
    JMenuItem otworzPlik = new JMenuItem("Otworz plik");
    JMenuItem zapiszPlik = new JMenuItem("Zapisz plik");
    JMenuItem  zakoncz = new JMenuItem("Zakończ");
    
    JMenu LewyPanel=new JMenu("Lewy panel");
    JMenuItem WyczyscLewy=new JMenuItem("Wyczyść lewy");
    
    JMenu PrawyPanel=new JMenu("Prawy panel");
    JMenuItem WyczyscPrawy=new JMenuItem("Wyczyść prawy");
    
    JMenu operacje = new JMenu("Operacje");
    JMenuItem rysujHist=new JMenuItem("Rysuj histogram");
    JMenuItem rysHistWyrownany=new JMenuItem("Rysuj histogram wyrównany");
    JMenuItem obrazDocelowy=new JMenuItem("Obraz docelowy");
    
    JMenuItem dystrybRozjas=new JMenuItem("Dystrybuanta rozjasnienie");
    JMenuItem dystrybuantaKontra=new JMenuItem("Dystrybuanta kontrastujaca");
    JMenuItem dystrybuantaZaciem=new JMenuItem("Dystrybuanta zaciemniajaca");
    JMenuItem dystrybuantaWyrow=new JMenuItem("Dystrybuanta wyrownujaca");
    
    public MenuOkna()
    {
        //menu Plik
        plik.add(otworzPlik);
        plik.add(zapiszPlik);        
        //linia oddzielająca JMenuItem        
        plik.add(new JSeparator());        
        plik.add(zakoncz);     
        add(plik);  
        
        LewyPanel.add(WyczyscLewy);
        add(LewyPanel);      
        
        PrawyPanel.add(WyczyscPrawy);
        add(PrawyPanel); 
        
        operacje.add(rysujHist);
        operacje.add(rysHistWyrownany);
        operacje.add(obrazDocelowy);
        operacje.add(dystrybRozjas);
        operacje.add(dystrybuantaKontra);
        operacje.add(dystrybuantaZaciem);
        operacje.add(dystrybuantaWyrow);
        
        add(operacje); 
       
    }
}
