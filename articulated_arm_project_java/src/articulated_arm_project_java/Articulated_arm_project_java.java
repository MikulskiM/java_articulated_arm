/*
 * 
 * "THE BEER-WARE LICENSE" (Revision 42):
 * Marek Mikulski and Maciej Kuraż wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy as a beer in return
 *
 */
package articulated_arm_project_java;


import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author Praca
 */
public class Articulated_arm_project_java extends JFrame{

    Articulated_arm_project_java(){
        
        super("JAVA PROJEKCIK - MIKULSKI, KURAŻ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

    
        GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas3D = new Canvas3D(config);
        canvas3D.setPreferredSize(new Dimension(1200,900));

        add(canvas3D);
        pack();
        setVisible(true);

        BranchGroup scena = create_scene();
	    scena.compile();

        SimpleUniverse universe = new SimpleUniverse(canvas3D);

        Transform3D przesuniecie_obserwatora = new Transform3D();               // ustawienie gdzie jest obserwator
        przesuniecie_obserwatora.set(new Vector3f(0.0f,0.0f,6.0f));

        universe.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);

        universe.addBranchGraph(scena);
        
        
        
        // ---------------- * MAGIC *   -> enables us to rotate everthing with the mouse
        OrbitBehavior orbit = new OrbitBehavior(canvas3D, OrbitBehavior.REVERSE_ROTATE);
        orbit.setSchedulingBounds(new BoundingSphere());
        universe.getViewingPlatform().setViewPlatformBehavior(orbit);

    }
    
    
    BranchGroup create_scene(){
        
      BranchGroup scena = new BranchGroup();
      
      Transform3D tmp_rot = new Transform3D();
      
      
      
//      BoundingBox bounds = new BoundingBox();
        
      Appearance  wyglad_podlogi = new Appearance();
      wyglad_podlogi.setColoringAttributes(new ColoringAttributes(0.2f,0.2f,0.2f,ColoringAttributes.NICEST));
      
      Box graniastoslup = new Box(8.0f, 0.01f, 8.0f, wyglad_podlogi);         // tworzę płaski graniastosłup, który słuzył nam będzie za podłoże
      
      Transform3D  p_podlogi = new Transform3D();       // przesuwam troche w dół, żeby była idealnie pod ramieniem robota
      p_podlogi.set(new Vector3f(0.0f,-0.7f,0.0f));

      p_podlogi.mul(tmp_rot);

      TransformGroup transformacja_podlogi= new TransformGroup(p_podlogi);

      transformacja_podlogi.addChild(graniastoslup);
      scena.addChild(transformacja_podlogi); 
      /*
      
      Color3f kolorSwiatlaBoxa = new Color3f(0.8f, 2.0f, 0.8f);     // ustawiam kolor światła, które rzucą na sferę
      
      Vector3f kierunekSwiatlaBoxa = new Vector3f(0, -5, -15);     // i ustawiam kierunek tego światła
      
//      BoundingSphere bounds = new BoundingSphere(new Point3d(0,0,0), 100);    // already defined ^^^
      
      DirectionalLight swiatloBoxa = new DirectionalLight(kolorSwiatlaBoxa, kierunekSwiatlaBoxa); // ustawiam kierunkowe światło żeby ładnie widać było 3D
      
      swiatloBoxa.setInfluencingBounds(bounds); // światło musi mieć jakieś granice
      
      scena.addChild(swiatloBoxa);   // dodaję do sceny nasze świato i mamy komplet (sfera + światło)
        
        */
      
      
//       Transform3D tmp_rot = new Transform3D();



    // ---------------------------------------    ZABAWA ŚWIATŁEM
      
      Material material_ramion = new Material(new Color3f(17.0f, 0.292f,0.0f), new Color3f(17.2f, 0.2f, 0.2f), 
                                             new Color3f(17.0f, 1.0f,0.0f), new Color3f(17.0f, 0.0f,1.0f), 128.0f);
      
      Material material_przegubow = new Material(new Color3f(0.3f, 0.3f,0.3f), new Color3f(0.3f,0.3f,0.3f),
                                                new Color3f(0.3f, 0.3f, 0.3f), new Color3f(0.3f, 0.3f, 0.3f), 20.0f);
      
      ColoringAttributes cattr = new ColoringAttributes();
      cattr.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
      
      Appearance wyglad_ramion = new Appearance();
      Appearance wyglad_przegubow = new Appearance();
      
      wyglad_ramion.setMaterial(material_ramion);
      wyglad_ramion.setColoringAttributes(cattr);
      wyglad_przegubow.setMaterial(material_przegubow);
      wyglad_przegubow.setColoringAttributes(cattr);
      
      
      
      Color3f kolor_swiatla_kier     = new Color3f(0.7f, 0.7f, 0.75f);
      BoundingSphere obszar_ogr =  new BoundingSphere(new Point3d(0.0d,0.0d,0.0d), 10.0d);
      Vector3f kierunek_swiatla_kier = new Vector3f(4.0f, -5.0f, -15.0f);
      
      DirectionalLight swiatlo_kier = new DirectionalLight(kolor_swiatla_kier, kierunek_swiatla_kier);
      swiatlo_kier.setInfluencingBounds(obszar_ogr);
      
      scena.addChild(swiatlo_kier);
      
      
     //PODSTAWA

      Appearance wygladPodstawy = new Appearance();
      wygladPodstawy.setColoringAttributes(new ColoringAttributes(0.5f,0.6f,0.6f,ColoringAttributes.NICEST));
     
      Cylinder podstawa = new Cylinder(0.8f, 0.2f, wyglad_przegubow);

      Transform3D  p_podstawy = new Transform3D();
      p_podstawy.set(new Vector3f(0.0f,-0.6f,0.0f));

      p_podstawy.mul(tmp_rot);

      TransformGroup transformacja_p= new TransformGroup(p_podstawy);

      transformacja_p.addChild(podstawa);
      scena.addChild(transformacja_p);
      
      
      // ---------------------------------------- KORPUS
      
      Appearance wygladKorpus = new Appearance();
      wygladKorpus.setColoringAttributes(new ColoringAttributes(2.4f,0.02f,0.02f,ColoringAttributes.NICEST));
     
      Box korpus = new Box(0.1f, 0.5f, 0.1f, wyglad_ramion);

      Transform3D p_korpus = new Transform3D();
      p_podstawy.set(new Vector3f(1.0f,0.0f,0.0f));
      p_korpus.mul(tmp_rot);

      TransformGroup transformacja_k = new TransformGroup(p_korpus);

      transformacja_k.addChild(korpus);
      scena.addChild(transformacja_k);
      
      //PRZEGUB 1

      Appearance wygladPrzegub = new Appearance();
      wygladPrzegub.setColoringAttributes(new ColoringAttributes(0.6f,0.6f,0.6f,ColoringAttributes.NICEST));
     
      Cylinder przegub_1 = new Cylinder(0.15f, 0.21f, wyglad_przegubow);

      Transform3D  p_przegub_1 = new Transform3D();
      p_przegub_1.set(new Vector3f(0.0f,0.58f,0.0f));

      tmp_rot.rotZ(Math.PI/2);
      tmp_rot.rotX(Math.PI/2);
      
      p_przegub_1.mul(tmp_rot);
      
      TransformGroup transformacja_p1 = new TransformGroup(p_przegub_1);

      transformacja_p1.addChild(przegub_1);
      scena.addChild(transformacja_p1);
      
      //RAMIE
      
      Appearance wygladRamie = new Appearance();
      wygladRamie.setColoringAttributes(new ColoringAttributes(2.4f,0.02f,0.02f,ColoringAttributes.NICEST));
     
      Box ramie = new Box(0.1f, 0.5f, 0.1f, wyglad_ramion);

      Transform3D p_ramie = new Transform3D();
      p_ramie.set(new Vector3f(-0.4f,1.0f,0.0f));
      
      tmp_rot.rotZ(Math.PI/4);
      
      p_ramie.mul(tmp_rot);
      
      TransformGroup transformacja_r = new TransformGroup(p_ramie);

      transformacja_r.addChild(ramie);
      scena.addChild(transformacja_r);
      
        //PRZEGUB 2
     
      Cylinder przegub_2 = new Cylinder(0.15f, 0.21f, wyglad_przegubow);

      Transform3D  p_przegub_2 = new Transform3D();
      p_przegub_2.set(new Vector3f(-0.82f,1.42f,0.0f));

      tmp_rot.rotZ(Math.PI/2);
      tmp_rot.rotX(Math.PI/2);
      
      p_przegub_2.mul(tmp_rot);
      
      TransformGroup transformacja_p2 = new TransformGroup(p_przegub_2);

      transformacja_p2.addChild(przegub_2);
      scena.addChild(transformacja_p2);
      
      //CHWYTAK
//     
     
      Box chwytak = new Box(0.1f, 0.25f, 0.1f, wyglad_ramion);

      Transform3D p_chwytak = new Transform3D();
      p_chwytak.set(new Vector3f(-1.08f,1.27f,0.0f));
      
      tmp_rot.rotZ(-Math.PI/3);
      
      p_chwytak.mul(tmp_rot);
      
      TransformGroup transformacja_c = new TransformGroup(p_chwytak);

      transformacja_c.addChild(chwytak);
      scena.addChild(transformacja_c);
      
      
      
        
        return scena;
    }
    
    
    
    
    
    
    public static void main(String[] args) {
        
        new Articulated_arm_project_java();
        ImageIcon iconic = new ImageIcon("pic.png");
        JOptionPane.showMessageDialog(null, "hey", "INSTRUCTIONS", JOptionPane.INFORMATION_MESSAGE, iconic);
        
        
        
    }
    
}
