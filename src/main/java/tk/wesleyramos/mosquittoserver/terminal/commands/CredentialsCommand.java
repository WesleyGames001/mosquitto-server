package tk.wesleyramos.mosquittoserver.terminal.commands;

import tk.wesleyramos.mosquittoserver.Mosquitto;
import tk.wesleyramos.mosquittoserver.MosquittoColor;
import tk.wesleyramos.mosquittoserver.terminal.Command;

import java.util.Arrays;
import java.util.List;

public class CredentialsCommand extends Command {

    @Override
    public boolean execute(String[] args) {
        if (args.length == 1 && Arrays.asList("criar", "create", "new").contains(args[0].toLowerCase())) {
            System.out.println(MosquittoColor.BLUE_BRIGHT + "[Credenciais]: " + MosquittoColor.WHITE_BRIGHT + Mosquitto.config.getNewCredentials() + MosquittoColor.RESET);
            return true;
        }

        if (args.length == 2 && Arrays.asList("deletar", "delete", "revoke", "revogar").contains(args[0].toLowerCase())) {
            if (Mosquitto.config.testCredentials(args[1])) {
                Mosquitto.config.revokeCredentials(args[1]);
                System.out.println(MosquittoColor.BLUE_BRIGHT + "[Credenciais]: " + MosquittoColor.WHITE_BRIGHT + "as credenciais foram revogadas!" + MosquittoColor.RESET);
                return true;
            }

            System.out.println(MosquittoColor.BLUE_BRIGHT + "[Credenciais]: " + MosquittoColor.WHITE_BRIGHT + "as credenciais fornecidas não foram encontradas." + MosquittoColor.RESET);
            return true;
        }

        System.out.println(MosquittoColor.BLUE_BRIGHT + "[Credenciais]: " + MosquittoColor.WHITE + "o comando enviado está incompleto:" + MosquittoColor.RESET);
        System.out.println();
        System.out.println(MosquittoColor.BLACK_BRIGHT + "               ↳ " + MosquittoColor.WHITE_BRIGHT + "credenciais criar" + MosquittoColor.RESET);
        System.out.println(MosquittoColor.BLACK_BRIGHT + "               ↳ " + MosquittoColor.WHITE_BRIGHT + "credenciais deletar <chave>" + MosquittoColor.RESET);
        System.out.println();

        return true;
    }

    @Override
    public String getName() {
        return "credenciais";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("credentials", "chaves", "keys", "senhas", "passwords");
    }
}
