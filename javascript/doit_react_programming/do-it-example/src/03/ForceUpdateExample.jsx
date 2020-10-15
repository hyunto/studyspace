import React, { Component } from "react";

class ForceUpdateExample extends Component {
  constructor(props) {
    super(props);

    // state 정의
    this.loading = true;
    this.formData = "no data";
    this.handleData = this.handleData.bind(this);
    
    // 4초 후에 handleData 함수 호출
    setTimeout(this.handleData, 4000);
  }

  handleData() {
    const data = "new data";

    // state 변경: setState() 함수 사용 안함
    this.loading = false;
    this.formData = data + this.formData;
    this.forceUpdate();
  }

  render() {
    return (
      <div>
        <span>로딩중: {String(this.loading)}</span>
        <span>결과: {this.formData}</span>
      </div>
    );
  }
}

export default ForceUpdateExample;
