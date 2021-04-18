package server;

public class DataBaseAuthService implements AuthService {
    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        return SqlHandler.getNicknameByLoginAndPassword(login, password);
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        return SqlHandler.registration(login, password, nickname);
    }


    @Override
    public boolean changeNick(String oldNickname, String newNickname) {
        return SqlHandler.changeNick(oldNickname, newNickname);
    }
}