/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import model.ClientModel;

/**
 *
 * @author Michael
 */
public class GameManager
{
    private Collection<ClientModel> gameList = Collections.synchronizedList(new ArrayList<ClientModel>());
}
