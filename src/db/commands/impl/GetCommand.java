package db.commands.impl;

import db.data.SimpleDBData;

public class GetCommand extends Command {
    private String name;

    public GetCommand(String name) {
        this.name = name;
    }

    @Override
    public void execute(SimpleDBData data) {
        System.out.println(data.getData(name));
    }


}
