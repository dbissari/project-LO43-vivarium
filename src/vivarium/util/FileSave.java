/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivarium.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

/**
 *
 * @author bright
 */
public class FileSave {
    
    public static void save(Object object, String file) throws FileNotFoundException, IOException {
        File outFile = new File(file);
        outFile.getParentFile().mkdir();
        final FileOutputStream fichier = new FileOutputStream(outFile);
        ObjectOutputStream oos;
        oos = new ObjectOutputStream(fichier);
        oos.writeObject(object);
        oos.flush();
        oos.close();
    }
    
    public static Object load(String file) throws FileNotFoundException, IOException, ClassNotFoundException {
        final FileInputStream fichier = new FileInputStream(file);
        ObjectInputStream ois;
        ois = new ObjectInputStream(fichier);
        Object content = ois.readObject();
        ois.close();
        
        return content;
    }
    
}
