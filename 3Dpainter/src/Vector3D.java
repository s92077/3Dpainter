import javafx.geometry.Point3D;
public class Vector3D
{
	private Point3D point;
	public Vector3D(){};
	//constructor(default)
	public Vector3D(double x,double y,double z)
	{
		point=new Point3D(x,y,z);
	}
	//constructor(multiply)
	public Vector3D(double a,Vector3D u)
	{
		point=new Point3D(a*u.getX(),a*u.getY(),a*u.getZ());
	}
	//return value
	public double getX(){return point.getX();}
	public double getY(){return point.getY();}
	public double getZ(){return point.getZ();}
	//show value
	public void show()
	{
		System.out.printf("(%f,%f,%f)\n",this.getX(),this.getY(),this.getZ());
	}
	//dot product(static/nonstatic)
	public static double dot(Vector3D a,Vector3D b)//dotproduct
	{
		return (a.getX()*b.getX())+(a.getY()*b.getY())+(a.getZ()*b.getZ());
	}
	public  double dot(Vector3D b)//dotproduct
	{
		return (this.getX()*b.getX())+(this.getY()*b.getY())+(this.getZ()*b.getZ());
	}
	//cross product(static/nonstatic)
	public static Vector3D cross(Vector3D a,Vector3D b)
	{
		Vector3D ans=new Vector3D(a.getY()*b.getZ()-a.getZ()*b.getY(),a.getZ()*b.getX()-a.getX()*b.getZ(),a.getX()*b.getY()-a.getY()*b.getX());
		return ans;
	}
	public  Vector3D cross(Vector3D b)
	{
		Vector3D ans=new Vector3D(this.getY()*b.getZ()-this.getZ()*b.getY(),this.getZ()*b.getX()-this.getX()*b.getZ(),this.getX()*b.getY()-this.getY()*b.getX());
		return ans;
	}
	//add function(static/nonstatic)
	public static Vector3D add(Vector3D a,Vector3D b)
	{
		Vector3D vect=new Vector3D(a.getX()+b.getX(),a.getY()+b.getY(),a.getZ()+b.getZ());
		return vect;
	}
	public  Vector3D add(Vector3D b)
	{
		Vector3D vect=new Vector3D(this.getX()+b.getX(),this.getY()+b.getY(),this.getZ()+b.getZ());
		return vect;
	}
	//negative way
	public Vector3D negate()
	{
		Vector3D vect=new Vector3D((-1)*this.getX(),(-1)*this.getY(),(-1)*this.getZ());
		return vect;
	}
	//multiply k times(static/nonstatic)
	public static Vector3D scalarMultiply(double k,Vector3D a)
	{
		Vector3D vect=new Vector3D(k*a.getX(),k*a.getY(),k*a.getZ());
		return vect;
	}
	public  Vector3D scalarMultiply(double k)
	{
		Vector3D vect=new Vector3D(k*this.getX(),k*this.getY(),k*this.getZ());
		return vect;
	}
	//get 2-Norm
	public double getNorm()
	{return Math.pow(this.getX()*this.getX()+this.getY()*this.getY()+this.getZ()*this.getZ(),0.5);}
	//get normalize Vector
	public Vector3D normalize()
	{
		double norm=this.getNorm();
		Vector3D vect;
		if(norm!=0)
			vect=new Vector3D(this.getX()/norm,this.getY()/norm,this.getZ()/norm);
		else
			vect=new Vector3D(0,0,0);
		//this.point=vect.getPoint();
		return vect;
	}
	public Vector3D rotVect(double phi,double theta){
		double vect[]={point.getX(),point.getY(),point.getZ()};
		phi=Math.toRadians(phi);
		theta=Math.toRadians(theta);
		double rxy=(vect[0])*(vect[0])+(vect[1])*(vect[1]);
		double op;

		if(vect[2]*vect[2]>rxy*rxy*100000){
			op=0;
		}
		else{
			if(vect[1]>=0)
				op=Math.acos(vect[0]/Math.sqrt(rxy));
			else
				op=-Math.acos(vect[0]/Math.sqrt(rxy));
		}
		//System.out.printf("%f %f\n",ot,op);
		double rotary[][]=
		{
			{Math.cos(theta)*Math.cos(phi),		-Math.cos(theta)*Math.sin(phi),			Math.sin(theta)*Math.cos(phi)*Math.cos(op)-Math.sin(theta)*Math.sin(phi)*Math.sin(op)}, 
			{Math.cos(theta)*Math.sin(phi),		Math.cos(theta)*Math.cos(phi),			Math.sin(theta)*Math.cos(phi)*Math.sin(op)+Math.sin(theta)*Math.sin(phi)*Math.cos(op)}, 
			{0,									0,										Math.cos(theta)}
		};
		double result[] = {0,0,0};
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				result[i]+=rotary[i][j]*vect[j];
			}
		}
		return new Vector3D(result[0],result[1],result[2]-Math.sqrt(rxy)*Math.sin(theta));
	}
	public static boolean inside(int x,int y,int xPoints[],int yPoints[])
	{
		int t=0;
		for(int i=0;i<xPoints.length-1;i++)
		{
			double slope;
			if((xPoints[i]-xPoints[i+1])==0)
				slope=Integer.MAX_VALUE;
			else
				slope=((double)(yPoints[i]-yPoints[i+1]))/(xPoints[i]-xPoints[i+1]);
			double tmp=y-slope*x-yPoints[i]+slope*xPoints[i];
			if(Math.min(xPoints[i],xPoints[i+1])<x&&x<Math.max(xPoints[i],xPoints[i+1])&&tmp>0)
				t++;
		}
		if(t%2==0)
			return false;
		else
			return true;
	}
}