'use strict';

angular.module('consultorioOnlineUiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('heredoFamilyBkg', {
                parent: 'entity',
                url: '/heredoFamilyBkgs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.heredoFamilyBkg.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heredoFamilyBkg/heredoFamilyBkgs.html',
                        controller: 'HeredoFamilyBkgController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heredoFamilyBkg');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('heredoFamilyBkg.detail', {
                parent: 'entity',
                url: '/heredoFamilyBkg/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.heredoFamilyBkg.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heredoFamilyBkg/heredoFamilyBkg-detail.html',
                        controller: 'HeredoFamilyBkgDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heredoFamilyBkg');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HeredoFamilyBkg', function($stateParams, HeredoFamilyBkg) {
                        return HeredoFamilyBkg.get({id : $stateParams.id});
                    }]
                }
            })
            .state('heredoFamilyBkg.new', {
                parent: 'heredoFamilyBkg',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heredoFamilyBkg/heredoFamilyBkg-dialog.html',
                        controller: 'HeredoFamilyBkgDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    observation: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('heredoFamilyBkg', null, { reload: true });
                    }, function() {
                        $state.go('heredoFamilyBkg');
                    })
                }]
            })
            .state('heredoFamilyBkg.edit', {
                parent: 'heredoFamilyBkg',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heredoFamilyBkg/heredoFamilyBkg-dialog.html',
                        controller: 'HeredoFamilyBkgDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HeredoFamilyBkg', function(HeredoFamilyBkg) {
                                return HeredoFamilyBkg.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heredoFamilyBkg', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('heredoFamilyBkg.delete', {
                parent: 'heredoFamilyBkg',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/heredoFamilyBkg/heredoFamilyBkg-delete-dialog.html',
                        controller: 'HeredoFamilyBkgDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HeredoFamilyBkg', function(HeredoFamilyBkg) {
                                return HeredoFamilyBkg.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('heredoFamilyBkg', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
