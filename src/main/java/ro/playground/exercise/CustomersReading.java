package ro.playground.exercise;

import java.io.*;
import java.util.*;

// "c:/WORK_DRIVE/Projects/ing_test.txt"


public class CustomersReading {


	public static void main(String[] args) {
		// read file name from STDIN
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter customer file:");
		String fileName = sc.next();

		// read customers line by line
		List<Customer> customers = new ArrayList<>();
		try (FileInputStream fileInputStream = new FileInputStream(new File(fileName));
		     BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {

			String line = reader.readLine();
			while (line != null) {
				line = reader.readLine();

				if (line != null && !line.isEmpty()) {
					String[] customerDetails = line.split("\t");
					customers.add(new Customer(Integer.valueOf(customerDetails[0]), customerDetails[1], customerDetails[2]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// sort by groupId and name
		Collections.sort(customers, new Comparator<Customer>() {
			@Override
			public int compare(Customer customer1, Customer customer2) {
				int groupIdSort = customer1.getGroupId().compareTo(customer2.getGroupId());
				if (groupIdSort == 0) {
					// groupIds are equal, sort by name
					return customer1.getName().compareTo(customer2.getName());
				}
				return groupIdSort;
			}
		});

		// print to STDOUT
		for (Customer customer : customers) {
			System.out.println(customer);
		}
	}
}


class Customer {
	private Integer groupId;
	private String name;
	private String data;

	Customer(Integer groupId, String name, String data) {
		this.groupId = groupId;
		this.name = name;
		this.data = data;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public String getName() {
		return name;
	}

	public String getData() {
		return data;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"groupId=" + groupId +
				", name='" + name + '\'' +
				", data='" + data + '\'' +
				'}';
	}
}
