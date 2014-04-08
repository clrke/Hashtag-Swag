import java.io.*;
import java.util.ArrayList;

public class Main
{
	public static void main(String[] args)
	{
		try
		{
			try (BufferedReader reader = new BufferedReader(new FileReader(new File("input.txt"))))
			{
				int T;
				T = Integer.parseInt(reader.readLine());

				for (int t = 0; t < T; t++)
				{
					String line = reader.readLine();
					String[] specs = line.split(" ");
					int charLimit = Integer.parseInt(specs[0]);
					int H = Integer.parseInt(specs[1]);

					String tweet = "";
					String post = reader.readLine();

					if (post.length() <= charLimit)
					{
						tweet += post;
						charLimit -= post.length() + 1;
					}

					Hashtag[] hashtags = new Hashtag[H];
					for (int h = 0; h < H; h++)
					{
						line = reader.readLine();
						specs = line.split(" ");
						hashtags[h] = new Hashtag(specs[0], Integer.parseInt(specs[1]));
					}

					ArrayList<ArrayList<Hashtag>> possibilities = new ArrayList<ArrayList<Hashtag>>();
					ArrayList<Hashtag> possibility = new ArrayList<Hashtag>();
					findAllPossibilities(possibilities, possibility, hashtags, 0, charLimit);

					if (possibilities.size() != 0)
					{
						ArrayList<Hashtag> bestPossibility = possibilities.get(0);
						int bestValue = getValue(bestPossibility);

						for (int p = 1; p < possibilities.size(); p++)
						{
							int value = getValue(possibilities.get(p));
							if (value > bestValue)
							{
								bestValue = value;
								bestPossibility = possibilities.get(p);
							}
						}

						for (Hashtag hashtag : bestPossibility)
						{
							if (tweet.length() != 0) tweet += " ";
							tweet += hashtag.content;
						}
					}
					System.out.println(tweet);
				}
			}
		}
		catch (Exception error)
		{
			System.out.println("Error: " + error.getCause());
		}
	}

	private static void findAllPossibilities(ArrayList<ArrayList<Hashtag>> possibilities,
		ArrayList<Hashtag> possibility, Hashtag[] hashtags, int current, int charLimit)
	{
		for (int i = current; i < hashtags.length; i++)
		{
			ArrayList<Hashtag> newPossibility = (ArrayList<Hashtag>) possibility.clone();
			newPossibility.add(hashtags[i]);
			if (getLength(newPossibility) <= charLimit)
			{
				possibilities.add(newPossibility);
				findAllPossibilities(possibilities, newPossibility, hashtags, i + 1, charLimit);
			}
		}
	}

	private static int getLength(ArrayList<Hashtag> hashtags)
	{
		int length = 0;
		for (Hashtag hashtag : hashtags)
			length += hashtag.content.length() + 1;
		return length - (length == 0 ? 0 : 1);
	}

	private static int getValue(ArrayList<Hashtag> hashtags)
	{
		int value = 0;
		for (Hashtag hashtag : hashtags)
		{
			value += hashtag.value;
		}
		return value;
	}
}
