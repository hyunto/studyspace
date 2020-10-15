import React, {
  Component
} from 'react';
import logo from './logo.svg';
import './App.css';
import TodaysPlan from './03/TodaysPlan';
import PropComponent from './03/PropsComponent';
import ChildComponent from './03/ChildComponent';
import ChildComponent2 from './03/ChildComponent2';
import BooleanComponent from './03/BooleanComponent';
import DefaultPropsComponent from './03/DefaultPropsComponent';
import ChildProperty from './03/ChildProperty';

class App extends Component {
  render() {
    const array = [1, 2, 3];
    const obj = { name: '제목', age: 30 };
    const node = <h1>노드</h1>;
    const func = () => console.log('메시지');

    return (
      <div className="app">
        <h1 className="title">두잇!리액트 시작하기</h1>
        <hr/>
        <TodaysPlan/>
        <hr/>
        <PropComponent name="두잇 리액트"/>
        <hr/>
        <ChildComponent
          stringValue="message"
          boolValue={true}
          numValue={1}
          arrayValue={array}
          objValue={obj}
          nodeValue={node}
          funcValue={func}
        />
        <hr/>
        <div><b>지루할 때: </b><BooleanComponent bored/></div>
        <div><b>즐거울 때: </b><BooleanComponent/></div>
        <hr/>
        <ChildComponent2 objValue={obj} requiredStringValue="hello"/>
        <hr/>
        <DefaultPropsComponent/>
        <hr/>
        <ChildProperty>
          <div><span>자식 노드</span></div>
        </ChildProperty>
      </div>
    );
  }
}

export default App;