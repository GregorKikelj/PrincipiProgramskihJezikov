import java.util.*;
abstract class Izraz2 {
  public abstract int getId();
	public abstract double eval(Map<String, Double> env);

	public abstract String toString();
	public abstract String toASTString();
  public abstract Izraz2 poenostavi();
}

class Plus2 extends Izraz2{
  Izraz2 l;
  Izraz2 r;
  public Plus2(Izraz2 l, Izraz2 r){
    this.l=l;
    this.r=r;
  }
  public double eval(Map<String, Double> env){
    return l.eval(env)+r.eval(env);
  }
  public String toString(){
    return l+"+"+r;
  }
  public String toASTString(){
    return "(+ "+l.toASTString()+" "+r.toASTString()+")";
  }
  public int getId(){
    return 0;
  }
  public Izraz2 poenostavi(){
    var lch = l.poenostavi();
    var rch = r.poenostavi();
    if(lch.getId()==6&&rch.getId()==6){
      return new Stevilka2(this.eval(null)+"");
    }else{
      l=lch;
      r=rch;
      return this;
    }
  }
}
class Odstej2 extends Izraz2{
  Izraz2 l;
  Izraz2 r;
  public Odstej2(Izraz2 l, Izraz2 r){
    this.l=l;
    this.r=r;
  }
  public double eval(Map<String, Double> env){
    return l.eval(env)-r.eval(env);
  }
  public String toString(){
    return l+"-"+r;
  }
  public String toASTString(){
    return "(- "+l.toASTString()+" "+r.toASTString()+")";
  }
  public int getId(){
    return 1;
  }
  public Izraz2 poenostavi(){
    var lch = l.poenostavi();
    var rch = r.poenostavi();
    if(lch.getId()==6&&rch.getId()==6){
      return new Stevilka2(this.eval(null)+"");
    }else{
      l=lch;
      r=rch;
      return this;
    }
  }
}
class Krat2 extends Izraz2{
  Izraz2 l;
  Izraz2 r;
  public Krat2(Izraz2 l, Izraz2 r){
    this.l=l;
    this.r=r;
  }
  public double eval(Map<String, Double> env){
    return l.eval(env)*r.eval(env);
  }
  public String toString(){
    return l+"*"+r;
  }
  public String toASTString(){
    return "(* "+l.toASTString()+" "+r.toASTString()+")";
  }
  public int getId(){
    return 2;
  }
  public Izraz2 poenostavi(){
    var lch = l.poenostavi();
    var rch = r.poenostavi();
    if(lch.getId()==6&&rch.getId()==6){
      return new Stevilka2(this.eval(null)+"");
    }else{
      l=lch;
      r=rch;
      return this;
    }
  }
}

class Potenca2 extends Izraz2{
  Izraz2 l;
  Izraz2 r;
  public Potenca2(Izraz2 l, Izraz2 r){
    this.l=l;
    this.r=r;
  }
  public double eval(Map<String, Double> env){
    return Math.pow(l.eval(env), r.eval(env));
  }
  public String toString(){
    return l+"^"+r;
  }
  public String toASTString(){
    return "(^ "+l.toASTString()+" "+r.toASTString()+")";
  }
  public int getId(){
    return 3;
  }
  public Izraz2 poenostavi(){
    var lch = l.poenostavi();
    var rch = r.poenostavi();
    if(lch.getId()==6&&rch.getId()==6){
      return new Stevilka2(this.eval(null)+"");
    }else{
      l=lch;
      r=rch;
      return this;
    }
  }
}
class Oklepaj2 extends Izraz2{
  Izraz2 l;
  public Oklepaj2(Izraz2 l){
    this.l=l;
  }
  public double eval(Map<String, Double> env){
    return l.eval(env);
  }
  public String toString(){
    return "("+l+")";
  }
  public String toASTString(){
    return "( "+l.toASTString()+")";
  }
  public int getId(){
    return 4;
  }
  public Izraz2 poenostavi(){
    var lch = l.poenostavi();
    if(lch.getId()==6){
      return new Stevilka2(this.eval(null)+"");
    }else{
      l=lch;
      return this;
    }
  }
}
class Spremenljivka2 extends Izraz2{
  String name;
  public Spremenljivka2(String name){
    this.name=name;
  }
  public double eval(Map<String, Double> env){
    return env.getOrDefault(name, 0d);
  }
  public String toString(){
    return name;
  }
  public String toASTString(){
    return name;
  }
  public int getId(){
    return 5;
  }
  public Izraz2 poenostavi(){
    return this;
  }
}
class Stevilka2 extends Izraz2{
  String value;
  public Stevilka2(String name){
    this.value=name;
  }
  public double eval(Map<String, Double> env){
    return Double.parseDouble(value);
  }
  public String toString(){
    return value;
  }
  public String toASTString(){
    return value;
  }
  public int getId(){
    return 6;
  }
  public Izraz2 poenostavi(){
    return this;
  }
}
class Minus2 extends Izraz2{
  Izraz2 l;
  public Minus2(Izraz2 l){
    this.l=l;
  }
  public double eval(Map<String, Double> env){
    return -l.eval(env);
  }
  public String toString(){
    return "("+l+")";
  }
  public String toASTString(){
    return "(- "+l.toASTString()+")";
  }
  public int getId(){
    return 7;
  }
  public Izraz2 poenostavi(){
    var lch = l.poenostavi();
    if(lch.getId()==6){
      return new Stevilka2(this.eval(null)+"");
    }else{
      l=lch;
      return this;
    }
  }
}

public class Parser2 {
  public static Izraz2 parse(String s) throws Exception {
    String src= s.replace(" ", "");
    return aditivniIzraz(src);
  }
  // ⟨aditivni⟩ ::= ⟨odstevalni⟩ | ⟨aditivni-izraz⟩ + ⟨odstevalni⟩
  public static Izraz2 aditivniIzraz(String src) throws Exception {
    try{
      Izraz2 e = odstevalniIzraz(src);
      return e;
    }catch(Exception e){

    }
    for(int i=0;i<src.length();i++){
      if(src.charAt(i)=='+'){
        try{
          Izraz2 l = new Plus2(aditivniIzraz(src.substring(0, i)), odstevalniIzraz(src.substring(i+1, src.length())));
          return l;
        }catch(Exception e){

        }
      }
    }
    throw new Exception("Can't parse aditivni izraz");
  }
  // ⟨odstevalni⟩ ::= ⟨odstevalni⟩ | ⟨odstevalni⟩ - ⟨multiplikativni⟩ 
  public static Izraz2 odstevalniIzraz(String src) throws Exception {
    try{
      Izraz2 e = multiplikativniIzraz(src);
      return e;
    }catch(Exception e){

    }
    for(int i=0;i<src.length();i++){
      if(src.charAt(i)=='-'){
        try{
          Izraz2 l = new Odstej2(odstevalniIzraz(src.substring(0, i)), multiplikativniIzraz(src.substring(i+1, src.length())));
          return l;
        }catch(Exception e){

        }
      }
    }
    throw new Exception("Can't parse aditivni izraz");
  }
  // ⟨multiplikativni⟩ ::= ⟨potencirni⟩ | ⟨multiplikativni⟩ * ⟨potencirni⟩
  public static Izraz2 multiplikativniIzraz(String src) throws Exception {
    try{
      Izraz2 e = potencirniIzraz(src);
      return e;
    }catch(Exception e){

    }
    for(int i=0;i<src.length();i++){
      if(src.charAt(i)=='*'){
        try{
          Izraz2 l = new Krat2(multiplikativniIzraz(src.substring(0, i)), potencirniIzraz(src.substring(i+1, src.length())));
          return l;
        }catch(Exception e){

        }
      }
    }
    throw new Exception("Can't parse multiplikativni izraz");
  }
  // ⟨potencirni⟩ ::= ⟨nasprotni⟩ | ⟨nasprotni⟩ ^ ⟨potencirni⟩
  public static Izraz2 potencirniIzraz(String src) throws Exception {
    try{
      Izraz2 e = nasprotniIzraz(src);
      return e;
    }catch(Exception e){

    }
    for(int i=0;i<src.length();i++){
      if(src.charAt(i)=='^'){
        try{
          Izraz2 l = new Potenca2(nasprotniIzraz(src.substring(0, i)), potencirniIzraz(src.substring(i+1, src.length())));
          return l;
        }catch(Exception e){

        }
      }
    }
    throw new Exception("Can't parse multiplikativni izraz");
  }
  // ⟨nasprotni⟩ ::= - ⟨nasprotni⟩ | ⟨osnovni⟩
  public static Izraz2 nasprotniIzraz(String src) throws Exception {
    if(src.charAt(0)=='-'){
      try{
        Izraz2 e = new Minus2(nasprotniIzraz(src.substring(1)));
        return e;
      }catch(Exception e){

      }
    }
    try{
      Izraz2 e = osnovniIzraz(src);
      return e;
    }catch(Exception e){

    }
    throw new Exception("Can't parse nasprotni izraz");
  }
  // ⟨osnovni-izraz⟩ ::= ⟨spremenljivka⟩ | ⟨številka⟩ | ( ⟨aditivni-izraz⟩ )
  public static Izraz2 osnovniIzraz(String src) throws Exception {
    try{
      Izraz2 e = spremenljivka(src);
      return e;
    }catch(Exception e){

    }
    try{
      Izraz2 e = stevilka(src);
      return e;
    }catch(Exception e){

    }
    if(src.charAt(0)=='('&&src.charAt(src.length()-1)==')'){
      try{
        Izraz2 e = aditivniIzraz(src.substring(1, src.length()-1));
        return e;
      }catch(Exception e){

      }
    }
    throw new Exception("Osnovni parsing failed");
  }
  // ⟨spremenljivka⟩ ::= [a-zA-Z]+
  public static Izraz2 spremenljivka(String src) throws Exception {
    if(src.matches("[a-zA-Z]")){
      return new Spremenljivka2(src);
    }
    throw new Exception("Spremenljivka parsing failed");
  }
  // ⟨spremenljivka⟩ ::= [a-zA-Z]+
  public static Izraz2 stevilka(String src) throws Exception {
    if(src.matches("[0-9]+")){
      return new Stevilka2(src);
    }
    throw new Exception("Stevilka parsing failed");
  }

}
