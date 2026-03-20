import { GetBooksResponse, DefaultApi } from "../../lib/generated/openapi";

// This uses the generated client that does all the work for us.
// However, in many cases we may desire more control and use only the DTOs.
async function getBooks(): Promise<GetBooksResponse> {
  const api = new DefaultApi();
  return api.getBooks();
}

export default async function Home() {
  const booksResponse: GetBooksResponse = await getBooks();

  return (
    <main>
      <h1>Books</h1>
      <p>Here are the books from the backend API call:</p>

      <ul>
        {booksResponse.books?.map((b) => (
          <li key={b.isbn}>
            &quot;{b.title}&quot; by {b.author}
          </li>
        ))}
      </ul>
    </main>
  );
}
