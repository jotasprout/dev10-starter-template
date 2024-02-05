function Contact() {
  return (
    <main>
      <h1>Contact</h1>
      <form>
        <div>
          <label htmlFor="name">Your 🍓 Name</label>
          <input id="name" type="text" />
        </div>
        <div>
          <label htmlFor="msg">Your Message 💬</label>
          <textarea id="msg" />
        </div>

        <button type="submit" disabled>
          Don't Submit! 😼
        </button>
      </form>
    </main>
  );
}

export default Contact;
