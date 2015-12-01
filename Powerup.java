/**
 * Ship Weapon-powerup
 *
 * @author J�rgen Braseth (jorgebr@stud.ntnu.no)
 * @see GameObject
 */


public class Powerup extends GameObject  {
	
	/** 
	 *Konstruktor.
	 *
	 *oppretter objectet
	 *@param gr Gamerenderer objektet skal tegnes i
	 *@param x x-posisjonen ved start
	 *@param y y-posisjonen ved start
	 */
	public Powerup( GameRenderer gr,Integer x,Integer y, Integer option ) {
		super( gr, "levelup", x.intValue(), y.intValue() );
		_layer = GameRenderer.LAYER_POWERUP;
		_xSpeed = -2;
	}
	
	/** Flytter seg */
	public void action() {
		// NOOP
	}

	/**
	 *Utf�rer kollisjon.
	 *Om kollisjon med skip:
	 *kj�rer skipets levelUp() og kj�rer die()
	 *
	 *@param otherObject Objektet dette objektet kolliderer med
	 */
	public void collide( GameObject otherObject ) {
		try {
			Player ship = (Player)otherObject;
			ship.levelUp();
			die();
		} catch (ClassCastException e) {
			// NOOP
		}
	}

    public void outOfScreenAction() {
	    // NOOP
    }
}
