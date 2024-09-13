/* eslint-disable jsx-a11y/img-redundant-alt */
import React from 'react';
import '../styles/cards.css'

const Cards = ({item, handleClick}) => {
    const {title, author, price, img} = item;
  return (
    <div className="cards">
        <div className="image_box">
            <img src={img} alt="Image" />
        </div>
        <div className="details">
            <p>{title}</p>
            <p>{author}</p>
            <p>Price - Rs. {price}.00</p>
            <button onClick={()=>handleClick(item)} >Add to Cart</button>
        </div>
    </div>
  )
}

export default Cards