import java.awt.*;

/** En abstrakt klasse. Representerer et objekt som skal p� skjermen.
 *  Kan v�re skipet, monstre eller powerups.
 *	@author Erik Axel Nielsen (erikaxel@stud.ntnu.no)
 *	@author J�rgen Braseth (jorgebr@stud.ntnu.no)
 */

public abstract class GameObject {

	public boolean dead;

	/** x koordinatet p� skjermen */
	public int x;
	/** y koordinatet p� skjermen */
	public int y;
	/** Bredde p� objektet. Blir hentet fra bilde-st�rrelse */
	public int width;
	/** H�yde p� objektet. Blir hentet fra bilde-st�rrelse */
	public int height; // kanskje burde v�re private??
	/** Hvor lenge objektet har v�rt p� skjermen. */
	protected int _age;
	/** Laget objektet er i. M� spesifiseres av subklasser. */
	protected int _layer = 0;
	/** Helsen til objektet */
	protected int _health;
	/** Eventuel rustning */
	protected int _armor;
	/** Skade objektet gir */
	protected int _damage;
	/** Hastigheten i y-retning */
	protected float _ySpeed;
	/** Hastigheten i x-retning */
	protected float _xSpeed;
	/** GameRenderen som eier objektet. */
	protected Image[] _movie; // kan ogs� bare v�re ett bilde.
	protected GameRenderer _gameRenderer;
	private String _name;
	/**
	 * Konstrukt�r.
	 * @param gameRenderer GameRendereren som eier objektet.
	 * @param name Navnet til objektet. Blir default brukt som bildebasenavn
	 */
	GameObject( GameRenderer gameRenderer, String name, int objectX, int objectY ) {
		dead = false;
		_name = name;
		_damage = 1;
		_age = 0;
		_xSpeed = _ySpeed = 0;
		x = objectX;
		y = objectY;
		_gameRenderer = gameRenderer;
		_movie = ImageMap.getInstance().getImage( name );
		if( _movie[0] == null ) {
			Log.log( "Couldn't find image for GameObject. Exiting.");
			System.exit( 1 );
		}
		width = _movie[0].getWidth( null );
		height = _movie[0].getHeight( null );
	}

	/**
 	 * Gets which layer the object is in.
	 * @return the layer of the object.
	 */
    public int getLayer() {
	    return _layer;
    }
	/**
	 * Tegner seg selv p� skjermen.
	 * @param g Graphics objekt � tegne p�.
	 */
	public void draw(Graphics2D g) {
		int whichOne = _age % _movie.length;
		g.drawImage( _movie[ whichOne ], x, y, null  );
	}

	/**
	 * Denne vil bli kalt like f�r objektet blir tegnet.
	 * Underklasser b�r gj�re ting som flytting, skyting o.l i denne metoden.
	 */
	abstract protected void action();

	/**
	 * Denne kalles av renderloopen. S�rger for at action() blir kalt.
	 */
	public void doAction() {
		_age++;
		if( _age == 200 ) { // Todo: denne burde ikke v�re her. Men det g�r s� J�VLIG fort n�...
			_age = 0;
		}
		action();

		x += _xSpeed;
		y += _ySpeed;
		// Hvis vi g�r utenfor skjermen, h�ndter det!
		if( y + height > _gameRenderer.getHeight() || y < 0 ||
		    x + width > _gameRenderer.getWidth() || x < 0 ) {
				outOfScreenAction();
		}
	}

	/**
	*
	*/
	public void clearDraw( Graphics2D g ) {
		g.clearRect( x, y, width, height );
	}

	/**
	 * Skal bli kalt n�r objektet skal d�, fjernes eller noe slikt...
	 */

	public void die() {
		dead = true;
		_gameRenderer.removeObject( this );
	}

	/**
	 * Metode som skader objektet.
	 * @param dmg int med mengden skade
	 */
	public void hit(int dmg) {
		_health -= dmg;
		if (_health<=0)
			die();
	}
	public String getName() {
		return _name;
	}

	/**
	 * Denne metoden blir kalt n�r man merker at man er utenfor skjermen.
	 * Objekter d�r vanligvis hvis de er utenfor skjermen.
	 * Subklasser m� override hvis de skal ha egen oppf�rsel.
	 */
	public void outOfScreenAction() {
		die();
	}

	/**
     * Vil bli kalt hvis en kollisjon blir detektert.
     * @param otherObject Det andre objektet i kollisjonen.
     */
	public void collide( GameObject otherObject ) {
		otherObject.hit(_damage);
	}
}