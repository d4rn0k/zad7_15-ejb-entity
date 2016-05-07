package pl.jrj.game;

import javax.ejb.Remote;

/**
 * @author Konrad Szwedo
 * @version 0.5L
 */
@Remote
public interface IGameRemote {

    /**
     * Game register Method.
     *
     * @param hwork - numer zadania
     * @param album – numer albumu studenta
     * @return true if register pass, otherwise false
     */
    public boolean register(int hwork, String album);
}