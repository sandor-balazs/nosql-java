'use strict';

angular.module('ontimeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('department', {
                parent: 'entity',
                url: '/departments',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.department.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/department/departments.html',
                        controller: 'DepartmentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('department');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('department.detail', {
                parent: 'entity',
                url: '/department/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.department.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/department/department-detail.html',
                        controller: 'DepartmentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('department');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Department', function($stateParams, Department) {
                        return Department.get({id : $stateParams.id});
                    }]
                }
            })
            .state('department.new', {
                parent: 'department',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/department/department-dialog.html',
                        controller: 'DepartmentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('department', null, { reload: true });
                    }, function() {
                        $state.go('department');
                    })
                }]
            })
            .state('department.edit', {
                parent: 'department',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/department/department-dialog.html',
                        controller: 'DepartmentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Department', function(Department) {
                                return Department.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('department', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('department.delete', {
                parent: 'department',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/department/department-delete-dialog.html',
                        controller: 'DepartmentDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Department', function(Department) {
                                return Department.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('department', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
