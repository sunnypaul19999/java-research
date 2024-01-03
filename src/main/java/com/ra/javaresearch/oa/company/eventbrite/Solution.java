package com.ra.javaresearch.oa.company.eventbrite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Item {
  public int id;
  public String name;

  public Item(int id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String toString() {
    return String.format("Item ID: %1$s, Name: %2$s", this.id, this.name);
  }
}

class PagedResponse {
  public int pageNumber;
  public int itemsPerPage;
  public int totalCount;
  public List<Item> items;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("PageNumber: ").append(this.pageNumber).append("\n");
    sb.append("ItemsPerPage: ").append(this.itemsPerPage).append("\n");
    sb.append("TotalCount: ").append(this.totalCount).append("\n");
    sb.append("Items: ");
    for (Item item : this.items) {
      sb.append("\n").append("    ").append(item);
    }
    return sb.toString();
  }

  @Override
  public boolean equals(Object another) {
    return this.toString().equals(another.toString());
  }

  @Override
  public int hashCode() {
    return this.toString().hashCode();
  }
}

class Result {

  public static final int OLD_SERVICE_ITEMS_PER_PAGE = 10;

  static class PageSplit {
    int lastPageNumber;
    int totalElementsToCollectInLastPage;
    int totalElementsInLastPage;

    @Override
    public String toString() {

      return "PageSplit{"
          + "lastPageNumber="
          + lastPageNumber
          + ", totalElementsToCollectInLastPage="
          + totalElementsToCollectInLastPage
          + ", totalElementsInLastPage="
          + totalElementsInLastPage
          + '}';
    }
  }

  public static PageSplit lastPageSplit(int pageNumber, int itemsPerPage) {
    final PageSplit pageSplit = new PageSplit();

    final double oldPage = itemsPerPage * pageNumber / (double) OLD_SERVICE_ITEMS_PER_PAGE;

    final int lastPageNumber = (int) Math.ceil(oldPage);

    final List<Item> oldServiceReqItems = OldService.getRequest(lastPageNumber);

    final int totalElementsToCollectInLastPage =
        (int) (oldPage * OLD_SERVICE_ITEMS_PER_PAGE) % OLD_SERVICE_ITEMS_PER_PAGE;

    pageSplit.lastPageNumber = lastPageNumber;

    pageSplit.totalElementsToCollectInLastPage =
        (totalElementsToCollectInLastPage > 0)
            ? totalElementsToCollectInLastPage
            : oldServiceReqItems.size();
    pageSplit.totalElementsInLastPage = oldServiceReqItems.size();

    return pageSplit;
  }

  public static void newPolicyAddItemsInLast(PageSplit pageSplit, List<Item> items) {

    List<Item> oldServiceReqItems = OldService.getRequest(pageSplit.lastPageNumber);

    for (int i = 1; i <= pageSplit.totalElementsToCollectInLastPage; i++) {
      items.add(oldServiceReqItems.get(i - 1));
    }
  }

  public static List<Item> query(int pageNumber, int itemsPerPage) {
    final PageSplit pageSplit = lastPageSplit(pageNumber, itemsPerPage);
    List<Item> items = new ArrayList<>();
    List<Item> oldServiceReqItems;

    newPolicyAddItemsInLast(pageSplit, items);

    System.out.println(items);

    // new policy page will not split if items-per-page in old-policy not equals items-per-page in new-policy
    // when this happens some items are bound split in previous to last pages
    if (itemsPerPage != OLD_SERVICE_ITEMS_PER_PAGE) {
      int page = pageSplit.lastPageNumber - 1;
      while (items.size() < itemsPerPage) {
        System.out.println("fetching page: " + page);
        oldServiceReqItems = OldService.getRequest(page);
        for (int i = oldServiceReqItems.size(); i >= 1; i--) {
          if (items.size() < itemsPerPage) {
            items.add(0, oldServiceReqItems.get(i - 1));
          }
        }
        page--;
      }
    }

    System.out.println(items);

    return items;
  }

  public static String abstractedRequest(int pageNumber, int itemsPerPage) {
    // final int oldServiceTotalPages = Globals.MAX_ITEMS / 10;
    final PageSplit pageSplit = lastPageSplit(pageNumber, itemsPerPage);
    System.out.println(Globals.MAX_ITEMS);
    System.out.println(pageNumber);
    System.out.println(itemsPerPage);
    System.out.println(pageSplit);
    // System.out.println(oldServiceTotalPages);
    if (pageNumber < 1
        || ((pageSplit.lastPageNumber - 1) * 10 + pageSplit.totalElementsToCollectInLastPage)
            > Globals.MAX_ITEMS) {
      return "404";
    } else {

      final PagedResponse pagedResponse = new PagedResponse();
      pagedResponse.pageNumber = pageNumber;
      pagedResponse.itemsPerPage = itemsPerPage;
      pagedResponse.totalCount = Globals.MAX_ITEMS;
      pagedResponse.items = query(pageNumber, itemsPerPage);

      return pagedResponse.toString();
    }
  }
}

public class Solution {
  public static void main(String[] args) throws IOException {
    //    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    //    BufferedWriter bufferedWriter = new BufferedWriter(new
    // FileWriter(System.getenv("OUTPUT_PATH")));
    //
    //    Globals.MAX_ITEMS = Integer.parseInt(bufferedReader.readLine().trim());
    //
    //    int page_number = Integer.parseInt(bufferedReader.readLine().trim());
    //
    //    int items_per_page = Integer.parseInt(bufferedReader.readLine().trim());
    //
    //    String result = Result.abstractedRequest(page_number, items_per_page);
    //
    //    bufferedWriter.write(result);
    //    bufferedWriter.newLine();
    //
    //    bufferedReader.close();
    //    bufferedWriter.close();

    //    PageNumber: 12
    //    ItemsPerPage: 10
    //    TotalCount: 114
    //    Items:
    //    Item ID: 111, Name: Item 111
    //    Item ID: 112, Name: Item 112
    //    Item ID: 113, Name: Item 113
    //    Item ID: 114, Name: Item 114

    Globals.MAX_ITEMS = 123;

    int page_number = 4;

    int items_per_page = 40;

    String result = Result.abstractedRequest(page_number, items_per_page);

    System.out.println(result);
  }
}

class OldService {

  public static List<Item> getRequest(int pageNumber) {
    int maxId = 114;
    int numberOfItems = 10;
    List<Item> results = new ArrayList<Item>();
    for (int i = 1; i <= numberOfItems; i++) {
      int id = ((pageNumber - 1) * numberOfItems) + i;
      if (id > maxId) {
        break;
      }
      results.add(new Item(id, "Item " + String.valueOf(id)));
    }
    return results;
  }
}

class Globals {
  public static int MAX_ITEMS;
}
