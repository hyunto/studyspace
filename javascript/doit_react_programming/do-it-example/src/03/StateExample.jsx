import React, { Component } from 'react';

class StateExample extends Component {
  constructor(props) {
    super(props);
    // state 정의
    this.state = {
      loading: true,
      formData: "no data",
    };

    this.handleData = this.handleData.bind(this);

    // 4초 후에 handleData 함수 호출
    setTimeout(this.handleData, 4000);
  }

  handleData() {
    const data = "new data";
    const { formData } = this.state;

    // state 변경
    this.setState({
      loading: false,
      formData: data + formData,
    });

    console.log("loading 값", this.state.loading);
  }

  render() {
    return (
      <div>
        <span>로딩중: {String(this.state.loading)}</span>
        <span>결과: {this.state.formData}</span>
      </div>
    );
  }
}

export default StateExample;