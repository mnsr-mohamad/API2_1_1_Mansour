package mvp.model;

import Classes.Local;

import java.util.List;

public interface DAOLocal {


    Local addLocal (Local Local);

    boolean removeLocal(Local Local);

    List<Local> getLocal();

    Local updateLocal(Local Local);

    Local readLocal(int Local  );
}
