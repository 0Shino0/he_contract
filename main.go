package main

import (
	"encoding/json"
	"errors"
	"fmt"
	"github.com/hyperledger/fabric-chaincode-go/shim"
	"github.com/hyperledger/fabric-protos-go/peer"
	"strconv"
)

/**
合同相关链码
*/

// ContractChainCode => 声明结构体
type ContractChainCode struct {
}

const CONTRACT = "contract_"
const USER = "user_"

// Contract 电子合同结构体
type Contract struct {
	// 合同id
	ContractId int `json:"contract_id"`
	// 合同标题
	ContractTitle string `json:"contract_title"`
	// 合同描述
	ContractDescription string `json:"contract_description"`
	// 合同类别
	ContractType string `json:"contract_type"`
	// ipfs链值
	ContractHash string `json:"contract_hash"`
	// 甲方用户id
	FirstPartyId int `json:"user_id"`
	// 乙方用户id
	SecondPartyId int `json:"second_party"`
}

// User 用户结构体
type User struct {
	// 用户Id
	UserId int `json:"user_id"`
	// 姓名
	UserRealName string `json:"user_real_name"`
	// 证件号
	UserIdCard string `json:"user_id_card"`
	// 手机号
	UserPhone string `json:"user_phone"`
}

// 程序入口
func main() {
	err := shim.Start(new(ContractChainCode))
	if err != nil {
		fmt.Printf("创建合同链码时出错: %s", err)
	}
}

// Init => 链码初始化接口
func (ccc *ContractChainCode) Init(stub shim.ChaincodeStubInterface) peer.Response {
	fmt.Println("初始化合同链码")
	return shim.Success(nil)
}

// Invoke => 链码调用接口
func (ccc *ContractChainCode) Invoke(stub shim.ChaincodeStubInterface) peer.Response {
	fmt.Println("调用函数")
	function, args := stub.GetFunctionAndParameters()
	switch function {
	case "addContract":
		return ccc.addContract(stub, args)
	case "addUser":
		return ccc.addUser(stub, args)
	case "updateContractById":
		return ccc.updateContractById(stub, args)
	case "updateUserById":
		return ccc.updateUserById(stub, args)
	case "getContractById":
		return ccc.getContractById(stub, args[0])
	case "getUserById":
		return ccc.getUserById(stub, args[0])
	case "deleteContractById":
		return ccc.deleteContractById(stub, args[0])
	default:
		return shim.Error("无效的调用函数名. 可用的函数为：" +
			" \"addContract\" \"addUser\" \"updateContractById\" \"updateUserById\" \"getContractById\" " +
			" \"getUserById\" \"deleteContractById\".")
	}
}

// 将字符串数组类型数据转换为"合同"结构体
func convertToContractFromArgs(args []string) (*Contract, error) {
	if len(args) != 7 {
		return nil, errors.New("参数数量不正确. 应输入如下7个参数" +
			" (合同id, 合同标题, 合同描述, 合同类别, ipfs链值，甲方用户id，乙方用户id)")
	}
	// 转换为int类型
	contractId, err := strconv.Atoi(args[0])
	if err != nil {
		return nil, errors.New("合同id必须是数字字符串")
	}
	contractTitle := args[1]
	contractDescription := args[2]
	contractType := args[3]
	contractHash := args[4]
	FirstPartyId, err := strconv.Atoi(args[5])
	if err != nil {
		return nil, errors.New("甲方用户id必须是数字字符串")
	}
	SecondPartyId, err := strconv.Atoi(args[6])
	if err != nil {
		return nil, errors.New("乙方用户id必须是数字字符串")
	}
	contract := &Contract{
		contractId,
		contractTitle,
		contractDescription,
		contractType,
		contractHash,
		FirstPartyId,
		SecondPartyId}
	return contract, nil
}

// 将字符串数组类型数据转换为"用户"结构体
func convertToUserFromArgs(args []string) (*User, error) {
	if len(args) != 4 {
		return nil, errors.New("参数数量不正确. 应输入如下4个参数" +
			" (用户Id, 姓名, 证件号, 手机号)")
	}
	// 转换为int类型
	userId, err := strconv.Atoi(args[0])
	if err != nil {
		return nil, errors.New("用户Id必须是数字字符串")
	}
	userRealName := args[1]
	userIdCard := args[2]
	userPhone := args[3]
	user := &User{
		userId,
		userRealName,
		userIdCard,
		userPhone}
	return user, nil
}

// 添加合同
func (ccc *ContractChainCode) addContract(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	// 将字符串数组类型数据转换为"合同"结构体
	contract, err := convertToContractFromArgs(args)
	if err != nil {
		return shim.Error(err.Error())
	}
	fmt.Println("contract:", contract)
	// 检查添加的部门ID是否已经存在
	contractIdAsString := strconv.Itoa(contract.ContractId)
	contractAsBytes, err := stub.GetState(CONTRACT + contractIdAsString)
	if err != nil {
		return shim.Error(err.Error())
	} else if contractAsBytes != nil {
		return shim.Error("此ID合同已存在")
	}
	// 结构体转换为json字符串
	contractAsJsonBytes, err := json.Marshal(contract)
	if err != nil {
		return shim.Error(err.Error())
	}
	// 保存合同到账本中
	err = stub.PutState(CONTRACT+contractIdAsString, contractAsJsonBytes)
	if err != nil {
		return shim.Error(err.Error())
	}
	return shim.Success(contractAsJsonBytes)
}

// 添加用户
func (ccc *ContractChainCode) addUser(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	// 将字符串数组类型数据转换为"用户"结构体
	user, err := convertToUserFromArgs(args)
	if err != nil {
		return shim.Error(err.Error())
	}
	fmt.Println("user:", user)
	// 检查添加的部门ID是否已经存在
	userIdAsString := strconv.Itoa(user.UserId)
	userAsBytes, err := stub.GetState(USER + userIdAsString)
	if err != nil {
		return shim.Error(err.Error())
	} else if userAsBytes != nil {
		return shim.Error("此ID用户已存在")
	}
	// 结构体转换为json字符串
	userAsJsonBytes, err := json.Marshal(user)
	if err != nil {
		return shim.Error(err.Error())
	}
	// 保存到账本中
	err = stub.PutState(USER+userIdAsString, userAsJsonBytes)
	if err != nil {
		return shim.Error(err.Error())
	}
	return shim.Success(userAsJsonBytes)
}

// 更新合同
func (ccc *ContractChainCode) updateContractById(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	// 将字符串数组类型数据转换为"合同"结构体
	contract, err := convertToContractFromArgs(args)
	if err != nil {
		return shim.Error(err.Error())
	}
	fmt.Println("contract:", contract)
	// 检查添加的部门ID是否已经存在
	contractIdAsString := strconv.Itoa(contract.ContractId)
	contractAsBytes, err := stub.GetState(CONTRACT + contractIdAsString)
	if err != nil {
		return shim.Error(err.Error())
	} else if contractAsBytes == nil {
		return shim.Error("此ID合同不存在")
	}
	/*
	 * State DB是一个Key-Value数据库，如果我们指定的Key在数据库中已经存在，那么就是修改操作。
	 * 如果Key不存在，那么就是插入操作。
	 */
	// 结构体转换为json字符串
	contractAsJsonBytes, err := json.Marshal(contract)
	if err != nil {
		return shim.Error(err.Error())
	}
	// 保存到账本中
	err = stub.PutState(CONTRACT+contractIdAsString, contractAsJsonBytes)
	if err != nil {
		return shim.Error(err.Error())
	}
	return shim.Success(contractAsJsonBytes)
}

// 更新用户
func (ccc *ContractChainCode) updateUserById(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	// 将字符串数组类型数据转换为"用户"结构体
	user, err := convertToUserFromArgs(args)
	if err != nil {
		return shim.Error(err.Error())
	}
	fmt.Println("user:", user)
	// 检查添加的部门ID是否已经存在
	userIdAsString := strconv.Itoa(user.UserId)
	userAsBytes, err := stub.GetState(USER + userIdAsString)
	if err != nil {
		return shim.Error(err.Error())
	} else if userAsBytes == nil {
		return shim.Error("此ID用户不存在")
	}
	// 结构体转换为json字符串
	userAsJsonBytes, err := json.Marshal(user)
	if err != nil {
		return shim.Error(err.Error())
	}
	// 保存到账本中
	err = stub.PutState(USER+userIdAsString, userAsJsonBytes)
	if err != nil {
		return shim.Error(err.Error())
	}
	return shim.Success(userAsJsonBytes)
}

// 通过合同id查询合同
func (ccc *ContractChainCode) getContractById(stub shim.ChaincodeStubInterface, contractId string) peer.Response {
	contractAsBytes, err := stub.GetState(CONTRACT + contractId)
	if err != nil {
		return shim.Error("查找合同失败:" + err.Error())
	} else if contractAsBytes == nil {
		return shim.Error("该id的合同不存在")
	}
	fmt.Printf("Search Response:%s\n", string(contractAsBytes))
	return shim.Success(contractAsBytes)
}

// 通过用户id查询用户
func (ccc *ContractChainCode) getUserById(stub shim.ChaincodeStubInterface, userId string) peer.Response {
	usertAsBytes, err := stub.GetState(USER + userId)
	if err != nil {
		return shim.Error("查找合同失败:" + err.Error())
	} else if usertAsBytes == nil {
		return shim.Error("该id的用户不存在")
	}
	fmt.Printf("Search Response:%s\n", string(usertAsBytes))
	return shim.Success(usertAsBytes)
}

// 通过合同id删除合同
func (ccc *ContractChainCode) deleteContractById(stub shim.ChaincodeStubInterface, contractId string) peer.Response {
	contractAsBytes, err := stub.GetState(CONTRACT + contractId)
	if err != nil {
		return shim.Error("查询出错:" + err.Error())
	} else if contractAsBytes == nil {
		return shim.Error("找不到对应id的合同")
	}

	err = stub.DelState(CONTRACT + contractId)
	if err != nil {
		return shim.Error("删除合同失败:" + CONTRACT + contractId + err.Error())
	}
	return shim.Success(nil)
}
