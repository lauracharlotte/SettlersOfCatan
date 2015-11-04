/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.command;

import server.facade.IModelFacade;

/**
 *
 * @author Michael
 */
public interface ICommand
{
    public String execute(IModelFacade facade);
}
