package club.plus1.ec_online.model.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import club.plus1.ec_online.App;
import club.plus1.ec_online.R;
import club.plus1.ec_online.domain.Product;

class Stock {

    private static Stock mInstance;
    List<Product> goods;

    private Stock() {
        goods = new ArrayList<>();
        readData();
    }

    static Stock getInstance() {
        if (mInstance == null) {
            mInstance = new Stock();
        }
        return mInstance;
    }

    private void readData() {
        InputStream is = App.getContext().getResources().openRawResource(R.raw.stock_free);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("Cp1251")));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\";\"");
                Product product;
                if ((tokens.length > 3) && (tokens[3].contains("."))) {
                    if (tokens[3].equals("Много")) {
                        product = new Product(tokens[0].trim().replace("\"", ""), tokens[1].trim(), tokens[2].trim(), 1000);
                    } else {
                        product = new Product(tokens[0].trim().replace("\"", ""), tokens[1].trim(), tokens[2].trim());
                        String[] parts = tokens[3].split("\\.");
                        product.count = Integer.parseInt(parts[0].replaceAll("\"", ""));
                    }
                    goods.add(product);
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
