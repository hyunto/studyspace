import React, { Component } from 'react';
import PropTypes from 'prop-types';

class PropsComponent extends Component {
  render() {
    return (
      <div className="message-container">
        {this.props.name}
      </div>
    );
  }
}

// 리엑트에서 제공하는 prop-types를 활용한 자료형 선언
// eslint-disable-next-line react/no-typos
PropsComponent.propTypes = {
  name: PropTypes.string,
};


export default PropsComponent;