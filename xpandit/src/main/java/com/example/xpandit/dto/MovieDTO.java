package com.example.xpandit.dto;

public class MovieDTO {

		//Title of the movie
		public String title;
		//The lunch date
		public String date;
		//Rating (0 to 10)
		public	String rank;
		//The revenue generated by the movie
		public String revenue;
		
		public MovieDTO(String title, String date, String rank, String revenue) {
			this.title = title;
			this.date = date;
			this.rank = rank;
			this.revenue = revenue;
		}
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getRank() {
			return rank;
		}
		public void setRank(String rank) {
			this.rank = rank;
		}
		public String getRevenue() {
			return revenue;
		}
		public void setRevenue(String revenue) {
			this.revenue = revenue;
		}
}
