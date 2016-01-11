package shoppinglist.persistence.shoppinglist;


import shoppinglist.data.ShoppingList;

import java.util.List;

/**
 * Created by Ramasinka on 2015.10.07.
 */
public class ShoppingListXmlPersistance implements ShoppingListPersistance {

    @Override
    public ShoppingList saveShoppingList(ShoppingList shoppingList) {
        return null;
    }

    @Override
    public ShoppingList getShoppingListById(int id) {
        return null;
    }

    @Override
    public List<ShoppingList> getAllShoppingLists() {
        return null;
    }

    @Override
    public void deleteShoppingList(ShoppingList shoppingList) {

    }

    @Override
    public ShoppingList updateShoppingList(ShoppingList shoppingList) {
        return null;
    }



   /* @Override
    public void persistance(ShoppingList shoppingList) {

//        List<ProductItem> productItems = shoppingList.getProductItemList();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = documentBuilder.newDocument();

        Element root = document.createElement("ShoppingList");
        document.appendChild(root);


        *//*for (ProductItem productItemm : productItems) {
            Element el = document.createElement("Product");
            root.appendChild(el);
            Attr id = document.createAttribute("ID");
            id.appendChild(document.createTextNode(productItemm.getProductId() + ""));
            el.setAttributeNode(id);
            Element name = document.createElement("Name");
            name.appendChild(document.createTextNode(productItemm.getProductName()));
            el.appendChild(name);

            Element age = document.createElement("Status");
            age.appendChild(document.createTextNode(productItemm.getStatus()));
            el.appendChild(age);
        }
        DOMSource source = new DOMSource(document);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        StreamResult streamResult = new StreamResult(new File("shoppingList.xml"));
        try {
            transformer.transform(source, streamResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }*//*
    }*/
}
