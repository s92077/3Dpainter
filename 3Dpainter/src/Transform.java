public class Transform
{
	private Vector3D viewPoint;
	private Vector3D visionVector;
	private int width,height;
	
	public Transform(Vector3D viewPoint,Vector3D visionVector,int width,int height)
	{
		this.viewPoint=viewPoint;
		this.visionVector=visionVector;
		this.width=width;
		this.height=height;
	}
	public Vector3D getviewPoint(){return viewPoint;}
	public Vector3D getVisionVector(){return visionVector;}
	public  Vector3D get2DXNorm()
	{
		Vector3D vectX=new Vector3D(visionVector.getY(),(-1)*(visionVector.getX()),0);
		return vectX.normalize();
	}
	public Vector3D get2DYNorm()
	{
		Vector3D vectY=Vector3D.cross(visionVector,get2DXNorm());
		return vectY.normalize();
	}
	public Vector3D getOrigin()
	{
		Vector3D vectXY=get2DXNorm();
		Vector3D vectZ=get2DYNorm().negate();
		
		vectXY=vectXY.scalarMultiply(width/(-2));
		vectZ=vectZ.scalarMultiply(height/(2));
		Vector3D vect=new Vector3D(viewPoint.getX(),viewPoint.getY(),viewPoint.getZ());
		
		vect=vect.add(visionVector);
		vect=vect.add(vectXY);
		vect=vect.add(vectZ);
		return vect;
	}
	public Vector3D projection(Vector3D endPoint)
	{
		Vector3D surfacePoint=Vector3D.add(viewPoint,visionVector);
		double value=visionVector.dot(surfacePoint);
		Vector3D lVector=endPoint.add(viewPoint.negate());
		double t=(value-(viewPoint.dot(visionVector)))/(visionVector.dot(lVector));
		Vector3D vect=viewPoint.add(lVector.scalarMultiply(t));
		return vect;
	}
	public Vector3D trans2D(Vector3D vect)
	{
		double x;
		double y;
		Vector3D X=get2DXNorm();
		Vector3D Y=get2DYNorm();
		
		double x2=(Y.getX()),y2=(Y.getY());
		
		double dx=vect.getX()-getOrigin().getX();
		double dy=vect.getY()-getOrigin().getY();
		double dz=vect.getZ()-getOrigin().getZ();
		
		y=(dz/Y.getZ());
		
		dx-=y*x2;
		dy-=y*y2;
		//System.out.printf("dx=%f\n",dx);
		x=Math.pow(dx*dx+dy*dy,0.5);
		if(dx*X.getX()+dy*X.getY()<0)
			x*=-1;
		Vector3D trans=new Vector3D(x,y,0);
		return trans;
	}
	public double getRatio(Vector3D position)
	{
		//System.out.printf("%f,%f%n",position.add(viewPoint.negate()).dot(visionVector),visionVector.getNorm());
		return position.add(viewPoint.negate()).dot(visionVector)/visionVector.getNorm()/visionVector.getNorm();
	}
}