public class Object 
{
	protected Vector3D position;
	protected Vector3D dirX=new Vector3D(1,0,0);
	protected Vector3D dirY=new Vector3D(0,1,0);
	protected Vector3D dirZ=new Vector3D(0,0,1);
	public Object(){}
	public Object(Vector3D position,Vector3D dirX,Vector3D dirY)
	{
		this.position=position;
		this.dirX=dirX;
		this.dirY=dirY;
		this.dirZ=Vector3D.cross(dirX,dirY);
	}
	public Vector3D getPosition(){return position;}
	public Vector3D getDirX(){return dirX;}
	public Vector3D getDirY(){return dirY;}
	public Vector3D getDirZ(){return dirZ; }
	
}