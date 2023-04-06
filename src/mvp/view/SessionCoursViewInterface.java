package mvp.view;

import Classes.SessionCours;
import mvp.presenter.SessionCoursPresenter;

import java.util.List;

public interface SessionCoursViewInterface {

    public void setPresenter(SessionCoursPresenter presenter);

    public void setListDatas(List<SessionCours> sessioncours);

    public void affMsg(String msg);

    public void affList(List infos);
}
