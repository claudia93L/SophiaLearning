// alerts

function showAlertSubscribe() {
  alert('Thank you for subscribing.');
}

function showAlertClearForm() {
  alert('The form has been cleared.');
}

// session storage cart

let cartItems = JSON.parse(sessionStorage.getItem('cartItems')) || [];

function addToCart(itemName) {
  cartItems.push(itemName);
  sessionStorage.setItem('cartItems', JSON.stringify(cartItems));
  alert('Item added to the cart: ' + itemName);
}

function displayCartItems() {
  let cartList = document.getElementById('cart-items');
  if (!cartList) return;
  cartList.innerHTML = '';

  if (cartItems.length > 0) {
    for (let i = 0; i < cartItems.length; i++) {
      let li = document.createElement('li');
      li.textContent = cartItems[i];
      cartList.appendChild(li);
    }
  } else {
    let li = document.createElement('li');
    li.textContent = 'Your cart is empty.';
    cartList.appendChild(li);
  }
}

function openCartModal() {
  let modal = document.getElementById('cart-modal');
  if (modal) modal.style.display = 'block';
}

function closeCartModal() {
  let modal = document.getElementById('cart-modal');
  if (modal) modal.style.display = 'none';
}

function showCart() {
  openCartModal();
  displayCartItems();
}

function clearCart() {
  cartItems = [];
  sessionStorage.removeItem('cartItems');
  displayCartItems();
  alert('Cart cleared.');
  closeCartModal();
}

function processOrder() {
  if (cartItems.length === 0) {
    alert('Your cart is empty. Add items before placing an order.');
    return;
  }
  cartItems = [];
  sessionStorage.removeItem('cartItems');
  displayCartItems();
  alert('Thank you for your order!');
  closeCartModal();
}

//local storage form

function submitForm() {
  let name = document.getElementById('full-name').value;
  let email = document.getElementById('contact-email').value;
  let phone = document.getElementById('phone').value;
  let feedback = document.getElementById('feedback');
  let custom = document.getElementById('custom-order').checked;

  if (!name || !email || !phone) {
    alert('Please fill in your name, email address, and/or phone number.');
    return false;
  }

  let customerInfo = {
    name: name,
    email: email,
    phone: phone,
    feedback: feedback,
    customOrder: custom,
    submittedAt: new Date().toISOString(),
  };

  let storageKey = 'customOrder_' + name;
  localStorage.setItem(storageKey, JSON.stringify(customerInfo));

  console.log('Saved to localStorage with key:', storageKey);
  console.log('Data:', customerInfo);

  let saved = JSON.parse(localStorage.getItem(storageKey));
  alert('Thank you for your message, ' + saved.name + '!');
  return true;
}

// modals

document.addEventListener('DOMContentLoaded', function () {
  let closeBtn = document.getElementById('close-cart-modal');
  if (closeBtn) {
    closeBtn.addEventListener('click', closeCartModal);
  }

  let modal = document.getElementById('cart-modal');
  if (modal) {
    modal.addEventListener('click', function (e) {
      if (e.target === modal) closeCartModal();
    });
  }
});
