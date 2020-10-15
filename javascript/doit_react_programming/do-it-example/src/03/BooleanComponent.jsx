import React, { Component } from 'react';
import PropTypes from 'prop-types';

class BooleanComponent extends Component {
  render() {
    const messasge = this.props.bored ? '놀러 가자' : '하던 일 열심히 마무리하기'
    return (
      <div className="message-container">
        {messasge}
      </div>
    );
  }
}

BooleanComponent.propTypes = {
  bored: PropTypes.bool
};

export default BooleanComponent;